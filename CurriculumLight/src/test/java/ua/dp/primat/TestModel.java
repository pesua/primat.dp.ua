/*
 *  Copyright 2010 Acer.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */

package ua.dp.primat;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import junit.framework.TestCase;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import ua.dp.primat.curriculum.data.Cathedra;
import ua.dp.primat.curriculum.data.Discipline;
import ua.dp.primat.curriculum.data.FinalControlType;
import ua.dp.primat.curriculum.data.HibernateUtil;
import ua.dp.primat.curriculum.data.IndividualControl;
import ua.dp.primat.curriculum.data.LoadCategory;
import ua.dp.primat.curriculum.data.RecordManager;
import ua.dp.primat.curriculum.data.StudentGroup;
import ua.dp.primat.curriculum.data.Workload;
import ua.dp.primat.curriculum.data.WorkloadEntry;
import ua.dp.primat.curriculum.data.WorkloadType;

/**
 *
 * @author Acer
 */
public class TestModel extends TestCase {

    public TestModel(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    // TODO add test methods here. The name must begin with 'test'. For example:
    // public void testHello() {}
    public void testNewEntry() {
        Cathedra cath = new Cathedra();
        cath.setName("EF");

        Discipline d = new Discipline();
        d.setName("test_subj");
        d.setCathedra(cath);

        StudentGroup sg = new StudentGroup();
        sg.setCode("EF");
        sg.setYear(new Long(2008));
        sg.setNumber(new Long(1));

        Workload w = new Workload();
        w.setDiscipline(d);
        w.setLoadCategory(LoadCategory.Normative);
        w.setType(WorkloadType.wtHumanities);

        WorkloadEntry we = new WorkloadEntry();
        we.setCourceWork(Boolean.TRUE);
        we.setFinalControl(FinalControlType.Exam);
        we.setLabCount(new Long(10));
        we.setIndCount(new Long(40));
        we.setLectionCount(new Long(20));
        we.setPracticeCount(new Long(30));
        we.setSemesterNumber(new Long(1));
        we.setWorkload(w);

        List<StudentGroup> vxStudentGroup = we.getGroups();
        vxStudentGroup.add(sg);
        we.setGroups(vxStudentGroup);

        IndividualControl ic = new IndividualControl();
        ic.setType("kr");
        ic.setWeekNum(new Long(1));
        ic.setWe(we);

        RecordManager.addCathedra(cath);
        RecordManager.addDiscipline(d);
        RecordManager.addWorkload(w);
        RecordManager.addStudentGroup(sg);
        RecordManager.addWorkloadEntry(we);
        RecordManager.addIndividualControl(ic);

        Session newSession = HibernateUtil.getSessionFactory().openSession();
        Transaction newTransaction = newSession.beginTransaction();
        List caths = newSession.createQuery("from Discipline").list();
        System.out.println( caths.size() + " disciplines found" );
        for ( Iterator iter = caths.iterator(); iter.hasNext(); ) {
            Discipline loadedMsg = (Discipline) iter.next();
            System.out.println( loadedMsg.getName() + " __ " + loadedMsg.getCathedra().getName() );
        }

        assertEquals(1, caths.size());
    }

}
