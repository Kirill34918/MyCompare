package com.mycompare;

/**
 * Класс MyStore хранит данные в формате JSON.
 * Данные сформированы на основе представления о хранении информации в таблицах БД
 * Каждая пара "name: []" содержит данные текущей таблицы
 * Каждя пара "{}" содержит данные по полям строки, в том числе все строки связанной дочерней таблицы
 *
 */
public class MyStore {

    /**
     * Первый источник данных в формате JSON.
     *
     */
    public static String json1 =
"{\n" +
"\t\"clients\": [\n" +
"\t\t{ \n" +
"\t\t\t\"id1\":\"123\",\n" +
"\t\t\t\"id\":\"123\",\n" +
"\t\t\t\"name\":\"Bank_A\", \n" +
"\t\t\t\"projects\": [ \n" +
"\t\t\t\t{\n" +
"\t\t\t\t\t\"id\":\"1234\", \n" +
"\t\t\t\t\t\"type\":\"Prod\",\n" +
"\t\t\t\t\t\"developers\": [ \n" +
"\t\t\t\t\t\t{\n" +
"\t\t\t\t\t\t\t\"id\":\"12345\", \n" +
"\t\t\t\t\t\t\t\"fname\":\"Петр\",\n" +
"\t\t\t\t\t\t\t\"lname\":\"Иванов\"\n" +
"\t\t\t\t\t\t},\n" +
"\t\t\t\t\t\t{\n" +
"\t\t\t\t\t\t\t\"id\":\"12346\", \n" +
"\t\t\t\t\t\t\t\"fname\":\"Иван\",\n" +
"\t\t\t\t\t\t\t\"lname\":\"Петров\"\n" +
"\t\t\t\t\t\t}\n" +
"\t\t\t\t\t]\n" +
"\t\t\t\t}, \n" +
"\t\t\t\t{\n" +
"\t\t\t\t\t\"id\":\"1235\", \n" +
"\t\t\t\t\t\"type\":\"Test\",\n" +
"\t\t\t\t\t\"developers\": [ \n" +
"\t\t\t\t\t\t{\n" +
"\t\t\t\t\t\t\t\"id\":\"12345\",\n" +
"\t\t\t\t\t\t\t\"fname\":\"Петр\", \n" +
"\t\t\t\t\t\t\t\"lname\":\"Иванов\"\n" +
"\t\t\t\t\t\t}\n" +
"\t\t\t\t\t]\n" +
"\t\t\t\t}\n" +
"\t\t\t] \n" +
"\t\t},\n" +
"\t\t{ \n" +
"\t\t\t\"id\":\"124\",\n" +
"\t\t\t\"name\":\"Insure_B\", \n" +
"\t\t\t\"projects\": [ \n" +
"\t\t\t\t{\n" +
"\t\t\t\t\t\"id\":\"1236\",\n" +
"\t\t\t\t\t\"type\":\"Prod\",\n" +
"\t\t\t\t\t\"developers\": [ \n" +
"\t\t\t\t\t\t{\n" +
"\t\t\t\t\t\t\t\"id\":\"12345\",\n" +
"\t\t\t\t\t\t\t\"fname\":\"Петр\",\n" +
"\t\t\t\t\t\t\t\"lname\":\"Иванов\"\n" +
"\t\t\t\t\t\t},\n" +
"\t\t\t\t\t\t{\n" +
"\t\t\t\t\t\t\t\"id\":\"12346\",\n" +
"\t\t\t\t\t\t\t\"fname\":\"Петр\",\n" +
"\t\t\t\t\t\t\t\"lname\":\"Иванов\"\n" +
"\t\t\t\t\t\t},\n" +
"\t\t\t\t\t\t{\n" +
"\t\t\t\t\t\t\t\"id\":\"12347\",\n" +
"\t\t\t\t\t\t\t\"fname\":\"Иван\",\n" +
"\t\t\t\t\t\t\t\"lname\":\"Петров\"\n" +
"\t\t\t\t\t\t}\n" +
"\t\t\t\t\t]\n" +
"\t\t\t\t}\n" +
"\t\t\t]\n" +
"\t\t}\n" +
"    ]\n" +
"}";

    /**
     * Второй источник данных в формате JSON.
     *
     */
    public static String json2 =
"{\n" +
"\t\"clients\": [\n" +
"\t\t{ \n" +
"\t\t\t\"id\":\"123\",\n" +
"\t\t\t\"name\":\"Bank_A1\", \n" +
"\t\t\t\"projects\": [ \n" +
"\t\t\t\t{\n" +
"\t\t\t\t\t\"id\":\"1234\", \n" +
"\t\t\t\t\t\"type\":\"Prod\",\n" +
"\t\t\t\t\t\"open_date\":\"08.08.2021\",\n" +
"\t\t\t\t\t\"developers\": [ \n" +
"\t\t\t\t\t\t{\n" +
"\t\t\t\t\t\t\t\"id\":\"12345\", \n" +
"\t\t\t\t\t\t\t\"fname\":\"Петр\",\n" +
"\t\t\t\t\t\t\t\"lname\":\"Иванов\"\n" +
"\t\t\t\t\t\t},\n" +
"\t\t\t\t\t\t{\n" +
"\t\t\t\t\t\t\t\"id\":\"12346\", \n" +
"\t\t\t\t\t\t\t\"fname\":\"Иван1\",\n" +
"\t\t\t\t\t\t\t\"lname\":\"Петров\"\n" +
"\t\t\t\t\t\t}\n" +
"\t\t\t\t\t]\n" +
"\t\t\t\t}, \n" +
"\t\t\t\t{\n" +
"\t\t\t\t\t\"id\":\"1235\", \n" +
"\t\t\t\t\t\"type\":\"Test\",\n" +
"\t\t\t\t\t\"developers\": [ \n" +
"\t\t\t\t\t\t{\n" +
"\t\t\t\t\t\t\t\"id\":\"12345\",\n" +
"\t\t\t\t\t\t\t\"fname\":\"Петр\", \n" +
"\t\t\t\t\t\t\t\"lname\":\"Иванов\"\n" +
"\t\t\t\t\t\t}\n" +
"\t\t\t\t\t]\n" +
"\t\t\t\t}\n" +
"\t\t\t] \n" +
"\t\t},\n" +
"\t\t{ \n" +
"\t\t\t\"id\":\"124\",\n" +
"\t\t\t\"name\":\"Insure_B\", \n" +
"\t\t\t\"projects\": [ \n" +
"\t\t\t\t{\n" +
"\t\t\t\t\t\"id\":\"1236\",\n" +
"\t\t\t\t\t\"type\":\"Prod\",\n" +
"\t\t\t\t\t\"developers\": [ \n" +
"\t\t\t\t\t\t{\n" +
"\t\t\t\t\t\t\t\"id\":\"12345\",\n" +
"\t\t\t\t\t\t\t\"fname\":\"Петр\",\n" +
"\t\t\t\t\t\t\t\"lname\":\"Иванов\"\n" +
"\t\t\t\t\t\t},\n" +
"\t\t\t\t\t\t{\n" +
"\t\t\t\t\t\t\t\"id\":\"12346\",\n" +
"\t\t\t\t\t\t\t\"fname\":\"Иван\",\n" +
"\t\t\t\t\t\t\t\"lname\":\"Петров\"\n" +
"\t\t\t\t\t\t}\n" +
"\t\t\t\t\t]\n" +
"\t\t\t\t}\n" +
"\t\t\t]\n" +
"\t\t}\n" +
"    ]\n" +
"}";
}
