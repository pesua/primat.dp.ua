/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ua.dp.primat.domain;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author pesua
 */
public enum LecturerType {
    ASSIATANT {

        @Override
        public String toString() {
            return BUNDLE.getString("lecturertype.assistant");
        }

    },
    SENIORLECTURER {

        @Override
        public String toString() {
            return BUNDLE.getString("lecturertype.seniorlecturer");
        }

    },
    DOCENT {

        @Override
        public String toString() {
            return BUNDLE.getString("lecturertype.docent");
        }
    },
    PROFESSOR {

        @Override
        public String toString() {
            return BUNDLE.getString("lecturertype.professor");
        }
    };

private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("dimainModel", new Locale("uk"));
}
