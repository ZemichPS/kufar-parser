package by.zemich.kufar.infrastructure.clients.dto;

import lombok.Data;

import java.util.List;

@Data
public class CategoriesDto {
    private List<CategoryDto> categories;

    @Data
    public static class CategoryDto {
        private String id;
        private String name;
        private String nameRu;
        private String nameBy;
        private String version;
        private String order;
        private List<SubcategoryDto> subcategories;

        @Data
        public static class SubcategoryDto {
            private String id;
            private String parent;
            private String order;
            private String name;
            private String redirect;
            private String nameRu;
            private String nameBy;
        }
    }
}

