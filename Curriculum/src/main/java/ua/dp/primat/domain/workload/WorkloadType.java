package ua.dp.primat.domain.workload;

import java.util.Locale;
import java.util.ResourceBundle;

public enum WorkloadType {
    wtHumanities(1) {

        @Override
        public String toString() {
            return LOCALIZATION.getString("workloadType.Humanities");
        }

    },
    wtNaturalScience(2) {

        @Override
        public String toString() {
            return LOCALIZATION.getString("workloadType.NaturalScience");
        }

    },
    wtProfPract(3) {

        @Override
        public String toString() {
            return LOCALIZATION.getString("workloadType.ProfPract");
        }

    },
    wtProfPractStudent(4) {

        @Override
        public String toString() {
            return LOCALIZATION.getString("workloadType.ProfPractStudent");
        }

    },
    wtProfPractUniver(5) {

        @Override
        public String toString() {
            return LOCALIZATION.getString("workloadType.ProfPractUniver");
        }

    };

    public static WorkloadType fromNumber(int idx) {
        for (WorkloadType wt : values()) {
            if (idx == wt.index) {
                return wt;
            }
        }

        //value for default
        return wtProfPract;
    }

    public int getIndex() {
        return index;
    }

    private WorkloadType(int idx) {
        index = idx;
    }

    private int index;

    private static final ResourceBundle LOCALIZATION = ResourceBundle.getBundle("dimainModel", new Locale("uk"));
}
