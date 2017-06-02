package it.emarolab.owloop.aMORDescriptor.utility.concept;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class MORFullConceptTest {

    private static MORFullConcept concept;

    @Before // called a before every @Test
    public void setUp() throws Exception {
        concept = new MORFullConcept(
                "Orientable", // the ground instance name
                "ontoName", // ontology reference name
                "src/test/resources/tboxTest.owl", // the ontology file path
                "http://www.semanticweb.org/emaroLab/luca-buoncompagni/sit" // the ontology IRI path
        );
    }

    @AfterClass // called after all @Test-s
    public static void save() throws Exception{
        concept.saveOntology( "src/test/resources/conceptTest.owl");
        System.out.println( "SAVED");
    }

    @Test
    public void subTest() throws Exception{
        concept.readSemantic();
        assertSemantic();
        concept.addSubConcept( "SubClass");
        concept.readSemantic();
        assertSemantic();
        concept.writeSemantic();
        assertSemantic();
        concept.addSubConcept( "SubClass");
        concept.writeSemantic();
        assertSemantic();
        concept.removeSubConcept( "SubClass");
        concept.readSemantic();
        assertSemantic();
        concept.removeSubConcept( "SubClass");
        concept.writeSemantic();
        assertSemantic();

        concept.addSubConcept( "Plane");
        concept.writeSemantic();
        assertSemantic();
        System.out.println( "described concept, sub test: " + concept.buildSubConcept());
    }

    @Test
    public void superTest() throws Exception{
        concept.readSemantic();
        assertSemantic();
        concept.addSuperConcept( "SuperClass");
        concept.readSemantic();
        assertSemantic();
        concept.writeSemantic();
        assertSemantic();
        concept.addSuperConcept( "SuperClass");
        // super class affect class definition during reasoning
        concept.writeSemanticInconsistencySafe();
        assertSemantic();
        concept.removeSuperConcept( "SuperClass");
        concept.readSemantic();
        assertSemantic();
        concept.removeSuperConcept( "SuperClass");
        // super class affect class definition during reasoning
        concept.writeSemanticInconsistencySafe();
        assertSemantic();

        concept.addSuperConcept( "Object");
        // super class affect class definition during reasoning
        concept.writeSemanticInconsistencySafe();
        assertSemantic();
        System.out.println( "described concept, super test: " + concept.buildSuperConcept());
    }

    @Test
    public void disjointTest() throws Exception{
        concept.readSemantic();
        assertSemantic();
        concept.addDisjointConcept( "DisjointClass");
        concept.readSemantic();
        assertSemantic();
        concept.writeSemantic();
        assertSemantic();
        concept.addDisjointConcept( "DisjointClass");
        concept.writeSemantic();
        assertSemantic();
        concept.removeDisjointConcept( "DisjointClass");
        concept.readSemantic();
        assertSemantic();
        concept.removeDisjointConcept( "DisjointClass");
        concept.writeSemantic();
        assertSemantic();

        concept.addDisjointConcept( "Scene");
        // disjoint class affect sub classes during reasoning
        concept.writeSemantic();
        assertSemantic();
        System.out.println( "described concept, disjoint test: " + concept.buildDisjointConcept());
    }

    @Test
    public void equivalentTest() throws Exception{
        concept.readSemantic();
        assertSemantic();
        concept.addEquivalentConcept( "EquivalentClass");
        concept.readSemantic();
        assertSemantic();
        concept.writeSemantic();
        assertSemantic();
        concept.addEquivalentConcept( "EquivalentClass");
        // equivalent class affect sub classes during reasoning
        concept.writeSemantic();
        assertSemantic();
        concept.removeEquivalentConcept( "EquivalentClass");
        concept.readSemantic();
        assertSemantic();
        concept.removeEquivalentConcept( "EquivalentClass");
        // equivalent class affect sub classes during reasoning
        concept.writeSemantic();
        assertSemantic();

        concept.addEquivalentConcept( "Scene");
        // equivalent class affect sub classes during reasoning
        concept.writeSemanticInconsistencySafe();
        assertSemantic();
        System.out.println( "described concept, equivalent test: " + concept.buildEquivalentConcept());
    }

    @Test
    public void defineTest() throws Exception{
        concept.readSemantic();
        assertSemantic();
        concept.addClassRestriction( "ClassRestriction");
        concept.readSemantic();
        assertSemantic();
        concept.writeSemantic();
        assertSemantic();
        concept.addClassRestriction( "ClassRestriction");
        concept.writeSemantic();
        assertSemantic();
        concept.addClassRestriction( "ClassRestriction");
        concept.readSemantic();
        assertSemantic();
        concept.addClassRestriction( "ClassRestriction");
        concept.writeSemantic();
        assertSemantic();
        concept.addClassRestriction( "ClassRestriction");
        concept.writeSemantic();
        assertSemantic();



        concept.readSemantic();
        assertSemantic();
        concept.addExactDataRestriction( "hasRestrictionProperty", 3, Long.class);
        concept.readSemantic();
        assertSemantic();
        concept.writeSemantic();
        assertSemantic();
        concept.addExactDataRestriction( "hasRestrictionProperty", 3, Long.class);
        concept.writeSemantic();
        assertSemantic();
        concept.addExactDataRestriction( "hasRestrictionProperty", 3, Long.class);
        concept.readSemantic();
        assertSemantic();
        concept.addExactDataRestriction( "hasRestrictionProperty", 3, Long.class);
        concept.writeSemantic();
        assertSemantic();

        concept.addExactDataRestriction( "hasRestrictionPropertyTest", 3, Double.class);
        concept.writeSemantic();
        assertSemantic();



        concept.readSemantic();
        assertSemantic();
        concept.addMinObjectRestriction( "hasRestrictionProperty", 3, "Plane");
        concept.readSemantic();
        assertSemantic();
        concept.writeSemantic();
        assertSemantic();
        concept.addMinObjectRestriction( "hasRestrictionProperty", 3, "Plane");
        concept.addMinObjectRestriction( "hasRestrictionProperty", 3, "Plane");
        concept.writeSemanticInconsistencySafe(); // the reasoner always infers here
        assertSemantic();
        concept.addMinObjectRestriction( "hasRestrictionProperty", 3, "Plane");
        concept.readSemantic();
        assertSemantic();
        concept.addMinObjectRestriction( "hasRestrictionProperty", 3, "Plane");
        concept.writeSemanticInconsistencySafe(); // the reasoner always infers here
        assertSemantic();

        concept.addMaxObjectRestriction( "hasRestrictionPropertyTest", 2, "Cone");
        concept.writeSemanticInconsistencySafe(); // the reasoner always infers here
        assertSemantic();

        System.out.println( "described data property, domain test: " + concept.getDefinitionConcept());
    }

    @Test
    public void classifyTest() throws Exception{
        concept.readSemantic();
        assertSemantic();
        concept.addIndividualClassified( "Individual-A");
        concept.readSemantic();
        assertSemantic();
        concept.writeSemantic();
        assertSemantic();
        concept.addIndividualClassified( "Individual-A");
        // equivalent class affect sub classes during reasoning
        concept.writeSemantic();
        assertSemantic();
        concept.removeIndividualClassified( "Individual-A");
        concept.readSemantic();
        assertSemantic();
        concept.removeIndividualClassified( "Individual-A");
        // equivalent class affect sub classes during reasoning
        concept.writeSemantic();
        assertSemantic();

        concept.setInstance( "Parameter");
        concept.readSemantic();
        System.out.println( "described concept, equivalent test: " + concept.buildIndividualClassified());

    }

    public void assertSemantic(){ // asserts that the state of the java representation is equal to the state of the ontology
        assertEquals( concept.getSubConcept(), concept.querySubConcept());
        assertEquals( concept.getSuperConcept(), concept.querySuperConcept());
        assertEquals( concept.getDisjointConcept(), concept.queryDisjointConcept());
        assertEquals( concept.getEquivalentConcept(), concept.queryEquivalentConcept());
        assertEquals( concept.getIndividualClassified(), concept.queryIndividualClassified());
        assertEquals( concept.getDefinitionConcept(), concept.queryDefinitionConcept());
    }
}