package ru.nsu.kuzminov.utils;

public enum ConnectionStatus {
    UNKNOWN, //Состояние подключения пока неизвестно
    CREATING_HOST, //Создание хоста
    HOSTING, //Хост создан
    CONNECTING, //Происходит подключение клиента к хосту
    CONNECTED //Клиент подключился к хосту
}