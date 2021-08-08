package com.mycompare;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ValueNode;

import java.io.IOException;
import java.util.*;

/**
 * Класс MyCompare сравнивает данные в формате JSON.
 */
public class MyCompare {

    public static void main(String[] args){
        MyCompare compare = new MyCompare();
        try {
            ObjectMapper mapper = new ObjectMapper();

            // Преобразовать первый набор данных в формате json в набор строк и сохранить результат в jsn1
            JsonNode root = mapper.readTree(MyStore.json1);
            List<String> jsn1 = new ArrayList<>();
            compare.addKeys("", root, jsn1, new ArrayList<>());

            // Преобразовать второй набор данных в формате json в набор строк и сохранить результат в jsn2
            root = mapper.readTree(MyStore.json2);
            List<String> jsn2 = new ArrayList<>();
            compare.addKeys("", root, jsn2, new ArrayList<>());

            // Вывести для информации итоговые наборы строк до поиска различий
            System.out.println(jsn1);
            System.out.println(jsn2);

            // Поиск различий с выводом результата
            MyCompare myCompare = new MyCompare();
            myCompare.Compare(jsn1, jsn2, "При сравнении первого со вторым набором");
            myCompare.Compare(jsn2, jsn1, "При сравнении второго с первым набором");
            myCompare.CompareCount(jsn1, jsn2, "При сравнении первого со вторым набором");

        } catch (IOException e) {  //JsonProcessingException
            e.printStackTrace();
        }
    }
    /**
     * Поиск различий данных в формате JSON с учетом их значения в группах (путях).
     * Например, "clients:projects:developers:fname=Петр",
     * где "clients:projects:developers:fname" - это группа, и "Петр" - это значение
     * Так json сформирован на основе представления о хранении информации в таблицах БД
     * будем учитывать, например, что порядок строк записей в таблицах может быть различен
     * а значит порядок строк в источниках json тоже будет различным,
     * но это будут одинаковые источники, если они больше ничем не отличаются.
     *
     * @param  string1  первый набор данных в формате JSON
     * @param  string2  второй набор данных в формате JSON
     * @param  comment  комментарий, с которого начинается заглавный коментарий
     */
    private void Compare(List<String> string1, List<String> string2, String comment) {
        List<String> unequal = new ArrayList<>();
        // Для каждой строки первого набора
        for (String str1 : string1) {
            boolean isNotEqual = true;
            // во всех строках второго набора
            for (String str2 : string2) {
                // ищем хотя бы одно полное совпадение
                if (str1.equals(str2)) {
                    isNotEqual = false;
                    break;
                }
            }
            // Если совпадение не найдено, то сохранить текущую строку в наборе несоответствия
            if (isNotEqual) {
                unequal.add(str1);
            }
        }
        //Вывод результатов
        if (unequal.size() > 0) {
            System.out.println(comment + " обнаружено различие в парах группа:значение");
            // Для каждого несоответствия
            for (int i = 0; i < unequal.size(); ++i) {
                String[] array1 = unequal.get(i).split("=");
                // вывести группу=значение первого набора
                System.out.println((i + 1) + ": " + unequal.get(i));
                // Подготовить строки точно с такими же группами, но другими значениями
                // схлопнув их при повторе за счет использования HashSet
                HashSet<String> set = new HashSet<>();
                for (String str2 : string2) {
                    String[] array2 = str2.split("=");
                    if (array1[0].equals(array2[0])) {
                        set.add(str2);
                    }
                }
                // И вывести все записи второго набора точно с такими же группами, но другими значениями
                Iterator<String> it = set.iterator();
                while (it.hasNext()) {
                    System.out.println("\tИ " + it.next());
                }
            }
        } else {
            System.out.println("Различие в парах группа:значение не обнаружено");
        }
    }

    /**
     * Поиск различий данных в формате JSON с учетом количества одинаковых пар группа=значение
     * Например, "clients:projects:developers:fname=Петр",
     * где "clients:projects:developers:fname" - это группа, и "Петр" - это значение
     * Так json сформирован на основе представления о хранении информации в таблицах БД
     * будем учитывать, например, что может быть повтор строк в таблице,
     * тогда если эти строки в источниках json повторяются одинаковое кол-во раз
     * то это будут одинаковые источники, но если кол-во повторов будет разное,
     * то в этом источники json будут различаться.
     * С точки зрения хранения общей информации такое различие в количестве может быть нормой,
     * но если речь идет о данных, которые представляют какие-то настройки,
     * вероятно, имеем дело с некорректным хранением данных, о чем и должен сигнализировать данный поиск.
     *
     * @param  string1  первый набор данных в формате JSON
     * @param  string2  второй набор данных в формате JSON
     * @param  comment  комментарий, с которого начинается заглавный коментарий
     */
    private void CompareCount(List<String> string1, List<String> string2, String comment){
        // Подготовить первый набор полных строк (группа=значение), в том числе количество их повтора в первом источнике json
        Map<String, Integer> map1 = new HashMap<>();
        for(String str: string1){
            if(map1.containsKey(str)){
                map1.put(str, map1.get(str) + 1);
            } else {
                map1.put(str, 1);
            }
        }
        // Подготовить второй набор полных строк (группа=значение), в том числе количество их повтора во втором источнике json
        HashMap<String, Integer> map2 = new HashMap<>();
        for(String str: string2){
            if(map2.containsKey(str)){
                map2.put(str, map2.get(str) + 1);
            } else {
                map2.put(str, 1);
            }
        }
        // Вывод одинаковых полных строк (группа=значение) с разными количествами повтора в двух наборов данных
        Iterator<Map.Entry<String, Integer>> it = map1.entrySet().iterator();
        boolean printMsg = false;
        while (it.hasNext()) {
            Map.Entry<String, Integer> map = it.next(); //(Map.Entry<String, Integer>)
            if (map2.containsKey(map.getKey()) && (map1.get(map.getKey()) != map2.get(map.getKey()))) {
                if (!printMsg){
                    printMsg = true;
                    System.out.println(comment + " обнаружено различие по количеству одинаковыхв пар группа:значение");
                }
                System.out.println("\t" + map.getKey() + ": " + map1.get(map.getKey()) + " И " + map2.get(map.getKey()));
            }
        }
    }

/**
 * Преобразует данные в формате json в набор строк в формате группа=значение
 * функция addKeys взята из источника: https://stackoverflow.com/questions/48642450/how-to-iterate-all-subnodes-of-a-json-object/48645692
 * так как была рекомендация использовать именно FASTERXML
 * Здесь я только изменил использование Map на List, чтобы поддержать возможность дублирование строк
 * и удалил лишние идентификаторы, которые нарушали наполнение и в которых уже не было необходимости
 *
 * @param  currentPath  текущий путь (группа)
 * @param  jsonNode  текущий узел данных JSON
 * @param  list  итоговый набор строк после обработки всего источника json
 */
     private void addKeys(String currentPath, JsonNode jsonNode, List<String> list, List<Integer> suffix) {
        if (jsonNode.isObject()) {
            ObjectNode objectNode = (ObjectNode) jsonNode;
            Iterator<Map.Entry<String, JsonNode>> iter = objectNode.fields();
            String pathPrefix = currentPath.isEmpty() ? "" : currentPath + ":";

            while (iter.hasNext()) {
                Map.Entry<String, JsonNode> entry = iter.next();
                addKeys(pathPrefix + entry.getKey(), entry.getValue(), list, suffix);
            }
        } else if (jsonNode.isArray()) {
            ArrayNode arrayNode = (ArrayNode) jsonNode;

            for (int i = 0; i < arrayNode.size(); i++) {
                suffix.add(i + 1);
                addKeys(currentPath, arrayNode.get(i), list, suffix);

                if (i + 1 <arrayNode.size()){
                    suffix.remove(arrayNode.size() - 1);
                }
            }
        } else if (jsonNode.isValueNode()) {
            ValueNode valueNode = (ValueNode) jsonNode;
            list.add(currentPath + "=" + valueNode.asText());
        }
    }
}
