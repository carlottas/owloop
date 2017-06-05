package it.emarolab.owloop.aMORDescriptor.utility.concept;
import it.emarolab.owloop.aMORDescriptor.utility.dataProperty.MORFullDataProperty;
import it.emarolab.owloop.aMORDescriptor.utility.individual.MORFullIndividual;
import it.emarolab.owloop.aMORDescriptor.MORAxioms;
import static org.junit.Assert.*;

import it.emarolab.owloop.core.Individual;
import it.emarolab.owloop.core.Semantic;
import jdk.internal.org.objectweb.asm.tree.analysis.Value;
import org.apache.jena.base.Sys;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;

import java.util.HashSet;
import java.util.Set;


public class scoreInitialization {
    private static MORFullIndividual score;
    private static MORFullIndividual totalScore;
    private static Set<String> subClasses=new HashSet<String>();
    private static Set<String> superClasses=new HashSet<String>();

    String ONTO_NAME="score-ontology";
    String FILE_PATH="src/test/resources/carlotta/score-ontology.owl";
    String IRI_ONTO="http://www.semanticweb.org/carlotta-sartore/scoreOntology";
    String SCORE_PROP_HAS_TIME="hasTime";
    String SCORE_PROP_HAS_VALUE="hasValue";
    String SCORE_PROP_NUMBER_BELONGING_INDIVIDUAL="NumberBelongingIndividual";
    String SCORE_PROP_NUMBER_EPISODIC_RETRIEVAL="NumberEpisodicRetrieval";
    String SCORE_PROP_NUMBER_RETRIEVAL="NumberRetrieval";
    String SCORE_PROP_NUMBER_SEMANTIC_RETRIEVAL="NumberSemanticRetrieval";
    String SCORE_PROP_NUMBER_SUB_CLASSES="NumberSubClasses";
    String SCORE_PROP_SCORE_BELONGING_INDIVIDUAL="ScoreBelongingIndividual";
    String SCORE_PROP_SCORE_SUB_CLASSES="ScoreSubClasses";
    String SCORE_PROP_TIMES_FORGOTTEN="TimesForgotten";
    String SCORE_PROP_TIMES_LOW_SCORE="TimesLowScore";
    String SCORE_PROP_USER_NO_FORGET="UserNoForget";
    //Object Property
    String SCORE_PROP_HAS_SCORE="hasScore";
    String SCORE_PROP_IS_SCORE_OF="isScoreOf";
    //Classes
    String SCORE_CLASS_SCENE="Scene";
    String SCORE_CLASS_SCORE="Score";
    String SCORE_CLASS_EPISODIC_SCORE="EpisodicScore";
    String SCORE_CLASS_SEMANTIC_SCORE="SemanticScore";
    String SCORE_CLASS_TOTAL_EPISODIC_SCORE="TotalEpisodicScore";
    String SCORE_CLASS_TOTAL_SEMANTIC_SCORE="TotalSemanticScore";
    String SCORE_CLASS_HIGH_SCORE="ScoreHigh";
    String SCORE_CLASS_EPISODIC_HIGH_SCORE="EpisodicScoreHigh";
    String SCORE_CLASS_SEMANTIC_HIGH_SCORE="SemanticScoreHigh";
    String SCORE_CLASS_LOW_SCORE="ScoreLow";
    String SCORE_CLASS_EPISODIC_LOW_SCORE="EpisodicScoreLow";
    String SCORE_CLASS_SEMANTIC_LOW_SCORE="SemanticScoreLow";
    String SCORE_CLASS_TO_BE_FORGOTTEN="ToBeForgotten";
    String SCORE_CLASS_EPISODIC_TO_BE_FORGOTTEN="EpisodicToBeForgotten";
    String SCORE_CLASS_SEMANTIC_TO_BE_FORGOTTEN="SemanticToBeForgotten";
    //individuals
    String SCORE_INDIVIDUAL_TOTAL_EPISODIC="totalEpisodic";
    String SCORE_INDIVIDUAL_TOTAL_SEMANTIC="totalSemantic";
    String SCORE_OBJ_PROP_IS_SUB_CLASS_OF="isSubClassOf";
    String SCORE_OBJ_PROP_IS_SUPER_CLASS_OF="isSuperClassOf";
    //wheights
    double SEMANTIC_WEIGHT_1=0.15;
    double SEMANTIC_WEIGHT_2=0.15;
    double SEMANTIC_WEIGHT_3=0.15;
    double SEMANTIC_WEIGHT_4=0.15;
    double SEMANTIC_WEIGHT_5=0.4;
    double EPISODIC_WEIGHT_1=0.4;
    double EPISODIC_WEIGHT_2=0.6;
    @Before // called a before every @Test
    public void setup() {
        score = new MORFullIndividual("score3",
                "score-ontology",
                "src/test/resources/carlotta/score-ontology.owl",
                "http://www.semanticweb.org/carlotta-sartore/scoreOntology");
        totalScore=new MORFullIndividual(SCORE_INDIVIDUAL_TOTAL_SEMANTIC,
                ONTO_NAME,
                FILE_PATH,
                IRI_ONTO);
     // subClasses.add("score0");
      //subClasses.add("score2");
      superClasses.add("score1");
      superClasses.add("score0");
      subClasses.add("score2");
    }
    @AfterClass // called after all @Test-s
    public static void save() throws Exception{
        score.saveOntology("src/test/resources/carlotta/score-ontology.owl" );
    }


    @Test
    public void semanticInitialization(){
        // add the individual to the class
        score.readSemantic();
        score.addTypeIndividual(SCORE_CLASS_SEMANTIC_SCORE);
        score.writeSemantic();
        score.readSemantic();
        //assertSemantic();
       // add the correpsonding data properties
        //TODO there is a problem that sometimes it adds the subclasses number two times
        //instead of one
        System.out.println( "added individual to the class "+ SCORE_CLASS_SEMANTIC_SCORE );
        score.addData(SCORE_PROP_NUMBER_BELONGING_INDIVIDUAL,0);
        score.addData(SCORE_PROP_SCORE_BELONGING_INDIVIDUAL,0.0);
        score.addData(SCORE_PROP_NUMBER_RETRIEVAL,1);
        score.addData(SCORE_PROP_NUMBER_SUB_CLASSES,subClasses.size());
        score.addData(SCORE_PROP_SCORE_SUB_CLASSES,computeSubClassesScore(subClasses));
        score.writeSemantic();
        score.readSemantic();
        //assertSemantic();
        System.out.println("added data prop");
        //compute the score
        double scoreComputed=computeScore(subClasses.size(),
                computeSubClassesScore(subClasses),
                0,
                0,
                1);
        score.addData(SCORE_PROP_HAS_SCORE,scoreComputed);
        score.writeSemantic();
        //assertSemantic();
        System.out.println("added score property");
        //updating the total score
        UpdateSemanticScore((float) scoreComputed);
        //adding the property is superClassOf
        if(!subClasses.isEmpty()) {
            for (String s : subClasses) {
                score.addObject(SCORE_OBJ_PROP_IS_SUPER_CLASS_OF, s);
            }
        }
        //addind data prop is subclass of
        if(!superClasses.isEmpty()) {
            for (String s : superClasses) {
                score.addObject(SCORE_OBJ_PROP_IS_SUB_CLASS_OF, s);
            }
        }
        score.writeSemantic();
        updateSuperClassScore(superClasses,(float) scoreComputed);
    }


    private double computeScore(int numberSubClasses,float scoreSubClasses,
                                int numberBelongingIndividual,float scoreIndividual,
                                int retrieval){
        totalScore.readSemantic();
        if(ValueOfDataPropertyFloat(totalScore.getDataIndividual(),SCORE_PROP_HAS_VALUE)==0){
            return (SEMANTIC_WEIGHT_5*retrieval);
        }
        return(   SEMANTIC_WEIGHT_1*numberBelongingIndividual+
                SEMANTIC_WEIGHT_2*scoreIndividual+
                SEMANTIC_WEIGHT_3*numberSubClasses+
                SEMANTIC_WEIGHT_4*scoreSubClasses/ValueOfDataPropertyFloat(totalScore.getDataIndividual(),SCORE_PROP_HAS_VALUE)+
                SEMANTIC_WEIGHT_5*retrieval);
    };
    private  double computeScore(int semantic_retrieval,
                                 int episodic_retrieval){
        return(EPISODIC_WEIGHT_1*semantic_retrieval+
                EPISODIC_WEIGHT_2*episodic_retrieval);

    };
    public void assertSemantic(){ // asserts that the state of the java representation is equal to the state of the ontology
        assertEquals( score.getDisjointIndividual(), score.queryDisjointIndividual());
        assertEquals(score.getEquivalentIndividual(), score.queryEquivalentIndividual());
        assertEquals( score.getTypeIndividual(), score.queryTypeIndividual());
        assertEquals( score.getObjectIndividual(), score.queryObjectIndividual());
        assertEquals( score.getDataIndividual(), score.queryDataIndividual());
    }
    public void UpdateSemanticScore(float scoreComputed){
        totalScore.readSemantic();
        MORAxioms.DataSemantics dataprop=totalScore.getDataIndividual();
        float oldTotal=ValueOfDataPropertyFloat(dataprop,SCORE_PROP_HAS_VALUE);
        System.out.println("adding new score to total score");
        System.out.print(oldTotal);
        oldTotal+=scoreComputed;
        System.out.print(oldTotal);
        totalScore.removeData(SCORE_PROP_HAS_VALUE);
        totalScore.writeSemantic();
        totalScore.addData(SCORE_PROP_HAS_VALUE,oldTotal);
        totalScore.writeSemantic();
      //  assertSemantic();
    }
    public void UpdateSemanticScore(float oldScore, float newScore){
        totalScore.readSemantic();
        MORAxioms.DataSemantics dataprop=totalScore.getDataIndividual();
        float total=ValueOfDataPropertyFloat(dataprop,SCORE_PROP_HAS_VALUE);
        System.out.println("modifying the score value");
        System.out.print(total);
        total-=oldScore;
        total+=newScore;
        System.out.print(total);
        totalScore.removeData(SCORE_PROP_HAS_VALUE);
        totalScore.writeSemantic();
        totalScore.addData(SCORE_PROP_HAS_VALUE,total);
        totalScore.writeSemantic();
        //assertSemantic();

    }
    public  float  computeSubClassesScore(Set<String> subclasses){
        if(subclasses.isEmpty()){
            return 0;
        }
        float total=0;
        for(String nameSubClass:subclasses){
            MORFullIndividual ind= new MORFullIndividual(nameSubClass,
                    ONTO_NAME,
                    FILE_PATH,
                    IRI_ONTO);
            ind.readSemantic();
            MORAxioms.DataSemantics dataProperties= ind.getDataIndividual();
            total+=ValueOfDataPropertyFloat(dataProperties,SCORE_PROP_HAS_SCORE);
            }

      return total;
    }
    // function which returns the first Float  value
    public float ValueOfDataPropertyFloat(MORAxioms.DataSemantics dataProperties, String dataPropertyName){
        for (MORAxioms.DataSemantic i:dataProperties){
            if(i.toString().contains(dataPropertyName)){
                String str = i.toString().replaceAll("[^\\d.]","");
                return Float.parseFloat(str.substring(1));
            }

        }
        return ((float)-1.0);
        }
   public void updateSuperClassScore(Set<String> setName,float scoreOld,float scoreNew){
       System.out.println("updating score of superclasses");
        //if the set of string is empty hence there is no super class the functions
       //automatically returns
       if(setName.isEmpty()){
           System.out.println("THERE IS NO SUPERCLASS");
           return;
       }
       //for all the string
       for (String name:setName) {
           //define the MOR individual of such superclass
           MORFullIndividual superClasses = new MORFullIndividual(
                   name,
                   ONTO_NAME,
                   FILE_PATH,
                   IRI_ONTO

           );
           System.out.println(name);
           //read the ontology
           superClasses.readSemantic();
           //take data property
           MORAxioms.DataSemantics dataProp=superClasses.getDataIndividual();
           //update the subclasses score with the new one
           float scoreSubClasses=ValueOfDataPropertyFloat(dataProp,SCORE_PROP_SCORE_SUB_CLASSES);
           scoreSubClasses-=scoreOld;
           scoreSubClasses+=scoreNew;
           //compute the new score
           float newScore=(float) computeScore((int)ValueOfDataPropertyFloat(dataProp,SCORE_PROP_NUMBER_SUB_CLASSES),
                   scoreSubClasses,
                   (int)ValueOfDataPropertyFloat(dataProp,SCORE_PROP_NUMBER_BELONGING_INDIVIDUAL),
                   ValueOfDataPropertyFloat(dataProp,SCORE_PROP_SCORE_BELONGING_INDIVIDUAL),
                   (int)ValueOfDataPropertyFloat(dataProp,SCORE_PROP_NUMBER_RETRIEVAL));
           //store the old score
           float oldScore=ValueOfDataPropertyFloat(dataProp,SCORE_PROP_HAS_SCORE);
           //change the value of the data prop score
           superClasses.removeData(SCORE_PROP_HAS_SCORE);
           superClasses.addData(SCORE_PROP_HAS_SCORE,newScore);
           superClasses.removeData(SCORE_PROP_SCORE_SUB_CLASSES);
           superClasses.addData(SCORE_PROP_SCORE_SUB_CLASSES,scoreSubClasses);
           //write the semantic
           superClasses.writeSemantic();
           superClasses.readSemantic();
           //find the super classes of such element
           MORAxioms.ObjectSemantics objProp = superClasses.getObjectIndividual();
           Set<String> classes = new HashSet<String>();
           //check if there is any superclasses
           for (MORAxioms.ObjectSemantic obj : objProp) {
               if (obj.toString().contains(SCORE_OBJ_PROP_IS_SUB_CLASS_OF)) {
                   MORAxioms.Individuals ind = obj.getValues();
                   for (OWLNamedIndividual i : ind) {
                       //add to the string the new score
                       classes.add(i.toStringID().substring(IRI_ONTO.length() + 1));
                   }

               }
           }
           //end check if subclasses
           System.out.println("updating total semantic");
           System.out.print(oldScore);
           System.out.println("oldScore");
           System.out.print(newScore);
           System.out.println("newScore");
           UpdateSemanticScore(oldScore,newScore);
           updateSuperClassScore(classes,oldScore,newScore);
       }
   }
   public void updateSuperClassScore(Set<String> setName,float score){
        //if the set of string is empty hence there is no super class the functions
        //automatically returns
       System.out.println("adding superclasses score");
        if(setName.isEmpty()){
            return;
        }
        //for all the string
        for (String name:setName) {
            //define the MOR individual of such superclass
            MORFullIndividual superClasses = new MORFullIndividual(
                    name,
                    ONTO_NAME,
                    FILE_PATH,
                    IRI_ONTO

            );
            System.out.println(name);
            //read the ontology
            superClasses.readSemantic();
            //take data property
            MORAxioms.DataSemantics dataProp=superClasses.getDataIndividual();
            //update the subclasses score with the new one
            float scoreSubClasses=ValueOfDataPropertyFloat(dataProp,SCORE_PROP_SCORE_SUB_CLASSES);
            scoreSubClasses+=score;
            int numberSubClasses=(int) ValueOfDataPropertyFloat(dataProp,SCORE_PROP_NUMBER_SUB_CLASSES);
            numberSubClasses++;
            //compute the new score
            float newScore=(float) computeScore(numberSubClasses,
                    scoreSubClasses,
                    (int)ValueOfDataPropertyFloat(dataProp,SCORE_PROP_NUMBER_BELONGING_INDIVIDUAL),
                    ValueOfDataPropertyFloat(dataProp,SCORE_PROP_SCORE_BELONGING_INDIVIDUAL),
                    (int)ValueOfDataPropertyFloat(dataProp,SCORE_PROP_NUMBER_RETRIEVAL));
            //store the old score
            float oldScore=ValueOfDataPropertyFloat(dataProp,SCORE_PROP_HAS_SCORE);
            //change the value of the data prop score
            superClasses.removeData(SCORE_PROP_HAS_SCORE);
            superClasses.addData(SCORE_PROP_HAS_SCORE,newScore);
            superClasses.addData(SCORE_PROP_HAS_SCORE,newScore);
            superClasses.removeData(SCORE_PROP_NUMBER_SUB_CLASSES);
            superClasses.addData(SCORE_PROP_NUMBER_SUB_CLASSES,numberSubClasses);
            superClasses.removeData(SCORE_PROP_SCORE_SUB_CLASSES);
            superClasses.addData(SCORE_PROP_SCORE_SUB_CLASSES,scoreSubClasses);
            //write the semantic
            superClasses.writeSemantic();
            superClasses.readSemantic();
            //find the super classes of such element
            MORAxioms.ObjectSemantics objProp = superClasses.getObjectIndividual();
            Set<String> classes = new HashSet<String>();
            //check if there is any superclasses
            for (MORAxioms.ObjectSemantic obj : objProp) {
                if (obj.toString().contains(SCORE_OBJ_PROP_IS_SUB_CLASS_OF)) {
                    MORAxioms.Individuals ind = obj.getValues();
                    for (OWLNamedIndividual i : ind) {
                        //add to the string the new score
                        classes.add(i.toStringID().substring(IRI_ONTO.length() + 1));
                    }

                }
            }
            //end check if subclasses
            System.out.println("updating total semantic");
            System.out.print(oldScore);
            System.out.println("oldScore");
            System.out.print(newScore);
            System.out.println("newScore");
            UpdateSemanticScore(oldScore,newScore);
            updateSuperClassScore(classes,oldScore,newScore);
        }
    }
}
