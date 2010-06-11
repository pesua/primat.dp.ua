package ua.dp.primat.domain.lesson;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author fdevelop
 */
public enum LessonType {
    LECTURE {

        @Override
        public String toString() {
            return BUNDLE.getString("lessontype.lecture");
        }

    },
    PRACTICE {

        @Override
        public String toString() {
            return BUNDLE.getString("lessontype.practice");
        }

    },
    LABORATORY {

        @Override
        public String toString() {
            return BUNDLE.getString("lessontype.laboratory");
        }

    };

    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("dimainModel", new Locale("uk"));
}
