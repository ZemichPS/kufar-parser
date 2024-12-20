package by.zemich.kufar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PriceAnalyzer {

    private static final BigDecimal OUTLIER_MULTIPLIER = BigDecimal.valueOf(1.5);

    /**
     * Вычисляет рыночную цену как среднее значение между средней ценой и медианной ценой
     * после удаления выбросов.
     */
    public BigDecimal getMarketPrice(List<BigDecimal> prices) {
        if (prices == null || prices.isEmpty()) {
            throw new IllegalArgumentException("Prices list cannot be null or empty");
        }

        // Фильтрация цен от выбросов
        List<BigDecimal> filteredPrices = removeOutliers(prices);

        // Вычисление средней цены и медианной цены
        BigDecimal meanPrice = calculateMean(filteredPrices);
        BigDecimal medianPrice = calculateMedian(filteredPrices);

        // Рыночная цена как среднее между средней и медианной
        BigDecimal marketPrice = meanPrice.add(medianPrice)
                .divide(BigDecimal.valueOf(2), RoundingMode.HALF_UP);

        // Вывод результатов (можно заменить на логирование в реальном приложении)
        System.out.println("Оригинальные цены: " + prices);
        System.out.println("Отфильтрованные цены: " + filteredPrices);
        System.out.println("Средняя цена: " + meanPrice);
        System.out.println("Медианная цена: " + medianPrice);
        System.out.println("Рыночная цена: " + marketPrice);

        return marketPrice;
    }

    /**
     * Убирает выбросы на основе межквартильного размаха (IQR).
     */
    public List<BigDecimal> removeOutliers(List<BigDecimal> prices) {
        List<BigDecimal> sortedPrices = new ArrayList<>(prices);
        Collections.sort(sortedPrices);

        // Вычисление первого и третьего квартилей
        BigDecimal q1 = calculatePercentile(sortedPrices, BigDecimal.valueOf(25));
        BigDecimal q3 = calculatePercentile(sortedPrices, BigDecimal.valueOf(75));

        // Рассчитываем IQR и границы для выбросов
        BigDecimal iqr = q3.subtract(q1);
        BigDecimal lowerBound = q1.subtract(OUTLIER_MULTIPLIER.multiply(iqr));
        BigDecimal upperBound = q3.add(OUTLIER_MULTIPLIER.multiply(iqr));

        System.out.println("межквартальный размах iqr: " + iqr);
        System.out.println("нижняя граница: " + lowerBound);
        System.out.println("верхняя граница: " + upperBound);

        // Возвращаем только те цены, которые находятся в допустимых пределах
        return sortedPrices.stream()
                .filter(price -> price.compareTo(BigDecimal.ZERO) > 0) // Фильтрация цен больше нуля
                .filter(price -> price.compareTo(lowerBound) >= 0 && price.compareTo(upperBound) <= 0)
                .collect(Collectors.toList());
    }

    /**
     * Рассчитывает персентиль (например, медиану или другие).
     */
    public BigDecimal calculatePercentile(List<BigDecimal> sortedPrices, BigDecimal percentile) {
        if (sortedPrices.isEmpty()) return BigDecimal.ZERO;

        // Индекс элемента, соответствующий персентилю
        BigDecimal index = percentile.divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(sortedPrices.size() - 1));

        int lowerIndex = (int) Math.floor(index.doubleValue());
        int upperIndex = (int) Math.ceil(index.doubleValue());

        // Если индексы совпадают, возвращаем элемент, если нет — интерполируем
        if (lowerIndex == upperIndex) {
            return sortedPrices.get(lowerIndex);
        } else {
            BigDecimal lowerValue = sortedPrices.get(lowerIndex);
            BigDecimal upperValue = sortedPrices.get(upperIndex);
            return lowerValue.add(upperValue.subtract(lowerValue)
                    .multiply(index.subtract(BigDecimal.valueOf(lowerIndex))));
        }
    }

    /**
     * Рассчитывает среднее значение списка цен.
     */
    public BigDecimal calculateMean(List<BigDecimal> prices) {
        if (prices.isEmpty()) return BigDecimal.ZERO;

        // Суммируем все цены и делим на количество элементов
        return prices.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(prices.size()), RoundingMode.HALF_UP);
    }

    /**
     * Рассчитывает медиану цен.
     */
    public BigDecimal calculateMedian(List<BigDecimal> prices) {
        if (prices.isEmpty()) return BigDecimal.ZERO;

        // Сортируем список цен
        List<BigDecimal> sortedPrices = new ArrayList<>(prices);
        Collections.sort(sortedPrices);

        int size = sortedPrices.size();
        if (size % 2 == 0) {
            // Если чётное количество элементов, медиана — это среднее значение двух центральных элементов
            return sortedPrices.get(size / 2 - 1)
                    .add(sortedPrices.get(size / 2))
                    .divide(BigDecimal.valueOf(2), RoundingMode.HALF_UP);
        } else {
            // Если нечётное, медиана — это центральный элемент
            return sortedPrices.get(size / 2);
        }
    }
}
