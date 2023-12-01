package com.niksahn.laba5.model;

public enum OperationRezult {
    Success, No_Right, In_DataBase, Internal_Error;

    public String toString() {
        return switch (this) {
            case Success -> "Успешно";
            case No_Right -> "Недостаточно прав";
            case In_DataBase -> "Пользователь уже записан на курс";
            case Internal_Error -> "Внутренняя ошибка";
        };
    }
    }
