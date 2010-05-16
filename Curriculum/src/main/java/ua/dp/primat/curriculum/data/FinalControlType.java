/*
 *  
 */

package ua.dp.primat.curriculum.data;

/**
 *
 * @author EniSh
 */
public enum FinalControlType {
    Exam,
    Setoff,
    DifferentiableSetoff,
    Nothing;

    @Override
    public String toString() {
        switch(this) {
            case Exam:
                return "Екзамен";
            case DifferentiableSetoff:
                return "Дифиринційовний залік";
            case Setoff:
                return "Залік";
            case Nothing:
                return "Нічого";
        }
    }
}
