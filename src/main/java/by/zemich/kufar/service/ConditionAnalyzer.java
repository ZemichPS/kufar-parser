package by.zemich.kufar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ConditionAnalyzer {

//    private final Pattern DEFECT_PATTERN = Pattern.compile(
//            "(?i)" + // Игнорировать регистр
//                    "(" +
//                    "разбит[аеоы]?\\s+(задн[её]е\\s+стекло|экран|стекло|камера)|" + // Разбит экран, заднее стекло и т.д.
//                    "скол\\s+на\\b|" + // Скол на
//                    "после\\s+(падения.*?полоса|ремонта)|" + // После падения появилась полоса, после ремонта
//                    "не\\s+работает|" + // Не работает
//                    "сильн[оа]\\s+разбит|" + // Сильно разбит
//                    "была заменена|" + // замена компонента
//                    "заменена|" + // замена компонента
//                    "не работает Face ID|" + // Не работает Face ID
//                    "не работает фэйс айди|" + // Не работает Face ID
//                    "разбит[аы]|" + // разбит
//                    "пот[еи]кла матрица" + // разбит (потекла матрица)
//                    "не ловит сеть|" + // не ловит сеть
//                    "заблокирован на iCloud|" + // на iCloud
//                    "заблокирован iCloud|" + // на iCloud
//                    "забыт пароль от iCloud|" + // на iCloud
//                    "забыли пароль от iCloud|" + // на iCloud
//                    "забыли пароль iCloud|" + // на iCloud
//                    "нет пароля от iCloud|" + // на iCloud
//                    "нет пароля iCloud|" + // на iCloud
//                    "вс[её] работает кроме|" + //  Все работает кроме
//                    "трещин[аы]|" + // Трещина, трещины
//                    "бит[оеы]\\s+пиксел[ьией]|" + // Битые пиксели
//                    "пару не значимых трещинок|" + // трещины
//                    "пару трещин|" + // трещины
//                    "не включается|" + // не включается
//                    "на запчасти|" + // не включается
//                    "после ремонта|" + // был в ремонте
//                    "под восстановление|" + // под восстановление
//                    "пару трещинок|" + // трещины
//                    "выгоревш[ие][йх]\\s+экран|" + // Выгоревший экран
//                    "поврежд[её]н[аоы]" + // Поврежден, повреждена
//                    ")"
//    );

    private final Pattern DEFECT_PATTERN = Pattern.compile(
            "(?i)(?x)" + // Игнорировать регистр и разрешить комментарии
                    "(" +
                    // Разбитый или поврежденный экран, стекло, камера
                    "разбит[аеоы]?\\s+(задн[её]е\\s+стекло|экран|стекло|камера)|" +
                    // Трещины, сколы и битые пиксели
                    "(скол\\s+на|трещин[аы]|бит[оеы]\\s+пиксел[ьией])|" +
                    // Проблемы с Face ID или другими компонентами
                    "не\\s+работает(?:\\s+(Face\\s?ID|фэйс\\s+айди))?|" +
                    // Проблемы после падения или ремонта
                    "после\\s+(падения.*?полоса|ремонта)|" +
                    // Сильно разбитые устройства
                    "сильн[оа]\\s+разбит|" +
                    // Поврежденные устройства
                    "поврежд[её]н[аоы]|" +
                    // Проблемы с iCloud
                    "заблокирован\\s+(?:на\\s+)?iCloud|" +
                    "забыт[ао]?\\s+пароль(?:\\s+от)?\\s+iCloud|" +
                    "нет\\s+пароля(?:\\s+от)?\\s+iCloud|" +
                    // Общие признаки неисправности
                    "не\\s+(включается|ловит\\s+сеть)|" +
                    "под\\s+восстановление|" +
                    "на\\s+запчасти|" +
                    "в\\s+нерабочем\\s+сост[оа]янии|" +
                    "не\\s+включается|" +
                    "пару\\s+(трещин|трещинок)|" +
                    "выгоревш[ие][йх]\\s+экран|" +
                    "вс[её]\\s+работает\\s+кроме" +
                    ")"
    );

    public boolean isFullyFunctional(String details) {
        if (details == null || details.isEmpty()) {
            return false;
        }
        return !DEFECT_PATTERN.matcher(details.toLowerCase()).find();
    }
}


