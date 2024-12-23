package by.zemich.kufar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ConditionAnalyzer {

    private final Pattern DEFECT_PATTERN = Pattern.compile(
            "(?i)" + // Игнорировать регистр
                    "(" +
                    "разбит[аеоы]?\\s+(задн[её]е\\s+стекло|экран|стекло|камера)|" + // Разбит экран, заднее стекло и т.д.
                    "скол\\s+на\\b|" + // Скол на
                    "после\\s+(падения.*?полоса|ремонта)|" + // После падения появилась полоса, после ремонта
                    "не\\s+работает|" + // Не работает
                    "сильн[оа]\\s+разбит|" + // Сильно разбит
                    "разбит[аы]|" + // разбит
                    "пот[еи]кла матрица" + // разбит (потекла матрица)
                    "не ловит сеть|" + // не ловит сеть
                    "вс[её] работает кроме|" + //  Все работает кроме
                    "трещин[аы]|" + // Трещина, трещины
                    "бит[оеы]\\s+пиксел[ьией]|" + // Битые пиксели
                    "пару не значимых трещинок|" + // трещины
                    "пару трещин|" + // трещины
                    "не включается|" + // не включается
                    "был в ремонте|" + // был в ремонте
                    "под восстановление|" + // под восстановление
                    "пару трещинок|" + // трещины
                    "царапин[аы]|" + // Царапина, царапины
                    "выгоревш[ие][йх]\\s+экран|" + // Выгоревший экран
                    "поврежд[её]н[аоы]" + // Поврежден, повреждена
                    ")"
    );

    public boolean isFullyFunctional(String details) {
        if (details == null || details.isEmpty()) {
            return false;
        }
        return !DEFECT_PATTERN.matcher(details.toLowerCase()).find();
    }
}
