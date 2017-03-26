$(function(){
    $(".ui-editor-group>.ui-editor-button").each(function(){
        var title = $(this).attr("title").toLowerCase();
        switch(title){
            case "bold":
                title = "Жирный";
                break;
            case "italic":
                title = "Курсив";
                break;
            case "underline":
                title = "Подчеркнутый";
                break;
            case "align text left":
                title = "Выровнять текст по левому краю";
                break;
            case "center":
                title = "Выровнять текст по центру";
                break;
            case "align text right":
                title = "Выровнять текст по правому краю краю";
                break;
            case "justify":
                title = "Выровнять";
                break;
            case "insert hyperlink":
                title = "Вставить гиперссылку";
                break;
            case "remove hyperlink":
                title = "Удалить гиперссылку";
                break;
            case "strikethrough":
                title = "Зачеркнуть";
                break;
            case "subscript":
                title = "Подстрочный индекс";
                break;
            case "superscript":
                title = "Надстрочный индекс";
                break;
            case "font":
                title = "Шрифт";
                break;
            case "font size":
                title = "Размер шрифта";
                break;
            case "style":
                title = "Стили";
                break;
            case "font color":
                title = "Цвет текста";
                break;
            case "text highlight color":
                title = "Цвет фона";
                break;
            case "remove formatting":
                title = "Удалить форматирование";
                break;
            case "bullets":
                title = "Вставить/удалить марикированный список";
                break;
            case "numbering":
                title = "Вставить/удалить нумерованный список";
                break;
            case "outdent":
                title = "Уменьшить отступ";
                break;
            case "indent":
                title = "Увеличить отступ";
                break;
            case "undo":
                title = "Отменить";
                break;
            case "redo":
                title = "Повторить";
                break;
            case "insert horizontal rule":
                title = "Вставить горизонтальную линию";
                break;
            case "insert image":
                title = "Вставить изображение";
                break;
            case "cut":
                title = "Вырезать";
                break;
            case "copy":
                title = "Копировать";
                break;
            case "paste":
                title = "Вставить";
                break;
            case "paste as text":
                title = "Вставить только текст";
                break;
            case "print":
                title = "Печать";
                break;
            case "show source":
                title = "Источник";
                break;
            case "show rich text":
                title = "Форматированный текст";
                break;
        }
        $(this).attr("title", title);
    })
});