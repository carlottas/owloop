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

public class scoreForgetting {
    private static MORFullIndividual scoreSemantic;
    private static MORFullIndividual totalScoreSemantic;
    private static MORFullIndividual scoreEpisodic;
    private static MORFullIndividual totalScoreEpisodic;
    private static MORFullConcept forgotClassSemantic;
    private static MORFullConcept toBeForgettingClassSemantic;
    private static MORFullConcept lowScoreClassSemantic;
    private static MORFullConcept forgotClassEpisodic;
    private static MORFullConcept toBeForgettingClassEpisodic;
    private static MORFullConcept lowScoreClassEpisodic;
    private static Set<String> subClasses=new HashSet<String>();
    private static Set<String> superClasses=new HashSet<String>();
    //string with ontology name of instances
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
    String SCORE_CLASS_FORGOTTEN="forgotten";
    String SCORE_CLASS_FORGOTTEN_EPISODIC="EpisodicForgotten";
    String SCORE_CLASS_FORGOTTEN_SEMANTIC="SemanticForgotten";
    //individuals
    String SCORE_INDIVIDUAL_TOTAL_EPISODIC="totalEpisodic";
    String SCORE_INDIVIDUAL_TOTAL_SEMANTIC="totalSemantic";
    String SCORE_OBJ_PROP_IS_SUB_CLASS_OF="isSubClassOf";
    String SCORE_OBJ_PROP_IS_SUPER_CLASS_OF="isSuperClassOf";
    String SCORE_OBJ_PROP_IS_INDIVIDUAL_OF="isIndividualOf";
    String SCORE_OBJ_PROP_HAS_INDIVIDUAL="hasIndividual";
    //wheights
    double SEMANTIC_WEIGHT_1=0.15;
    double SEMANTIC_WEIGHT_2=0.15;
    double SEMANTIC_WEIGHT_3=0.15;
    double SEMANTIC_WEIGHT_4=0.15;
    double SEMANTIC_WEIGHT_5=0.4;
    double EPISODIC_WEIGHT_1=0.4;
    double EPISODIC_WEIGHT_2=0.6;

    // String CLASSES_OF="scene1";
    String NAME_EPISODIC="score1";
    String NAME_SEMANTIC="scene2";
    @Before // called a before every @Test
    //set up of all the variables
    public void setup() {
        //set up of the score
        scoreSemantic = new MORFullIndividual(NAME_SEMANTIC,
                "score-ontology",
                "src/test/resources/carlotta/score-ontology.owl",
                "http://www.semanticweb.org/carlotta-sartore/scoreOntology");
        //set up of the total score
        totalScoreSemantic=new MORFullIndividual(SCORE_INDIVIDUAL_TOTAL_SEMANTIC,
                ONTO_NAME,
                FILE_PATH,
                IRI_ONTO);
        scoreEpisodic = new MORFullIndividual(NAME_EPISODIC,
                "score-ontology",
                "src/test/resources/carlotta/score-ontology.owl",
                "http://www.semanticweb.org/carlotta-sartore/scoreOntology");
        //set up of the total score
        totalScoreEpisodic=new MORFullIndividual(SCORE_INDIVIDUAL_TOTAL_EPISODIC,
                ONTO_NAME,
                FILE_PATH,
                IRI_ONTO);
        toBeForgettingClassEpisodic= new MORFullConcept(SCORE_CLASS_EPISODIC_TO_BE_FORGOTTEN,
                ONTO_NAME,
                FILE_PATH,
                IRI_ONTO);
        toBeForgettingClassSemantic=new MORFullConcept(SCORE_CLASS_SEMANTIC_TO_BE_FORGOTTEN,
                ONTO_NAME,
                FILE_PATH,
                IRI_ONTO);
        lowScoreClassSemantic=new MORFullConcept(SCORE_CLASS_SEMANTIC_LOW_SCORE,
                ONTO_NAME,
                FILE_PATH,
                IRI_ONTO);
        lowScoreClassEpisodic=new MORFullConcept(SCORE_CLASS_EPISODIC_LOW_SCORE,
                ONTO_NAME,
                FILE_PATH,
                IRI_ONTO);
        forgotClassEpisodic=new MORFullConcept(SCORE_CLASS_FORGOTTEN_EPISODIC,
                ONTO_NAME,
                FILE_PATH,
                IRI_ONTO);
        forgotClassSemantic= new MORFullConcept(SCORE_CLASS_FORGOTTEN_SEMANTIC,
                ONTO_NAME,
                FILE_PATH,
                IRI_ONTO);

    }
    @AfterClass // called after all @Test-s
    public static void save() throws Exception{
        forgotClassSemantic.saveOntology("src/test/resources/carlotta/score-ontology.owl" );
    }
    @Test
    public void forgetting(){
        // check the forgotten element:
        Set<String> EpisodicForgottenSet=new HashSet<String>();
        Set<String> SemanticForgottenSet=new HashSet<String>();
        forgotClassEpisodic.readSemantic();
        MORAxioms.Individuals forgottenEpisodic=forgotClassEpisodic.getIndividualClassified();
        for (OWLNamedIndividual i:forgottenEpisodic){

            EpisodicForgottenSet.add(i.toStringID().substring(IRI_ONTO.length() + 1));
        }
        forgotClassSemantic.readSemantic();
        MORAxioms.Individuals forgottenSemantic=forgotClassSemantic.getIndividualClassified();
        for (OWLNamedIndividual i:forgottenSemantic){
            SemanticForgottenSet.add(i.toStringID().substring(IRI_ONTO.length() + 1));
        }
        System.out.println("episodic forgot:\n");
        for (String s:EpisodicForgottenSet){
            System.out.println(s);
        }
        System.out.println("semantic forgot\n:");
        for (String s:SemanticForgottenSet){
            System.out.println(s);
        }
        //TODO eliminate the score once they have been comunicated and find a way to mantain the hiearcy
        //comunicate by the ontology itself ? maybe easier!

        //Check the element to be forgotten, increment the data property
        Set<String> EpisodicToBeForgotSet=new HashSet<String>();
        Set<String> SemanticToBeForgotSet=new HashSet<String>();

        System.out.println("Episodic Score that will be forgotten");
        updateTimes(toBeForgettingClassEpisodic,EpisodicToBeForgotSet,SCORE_PROP_TIMES_FORGOTTEN);

        System.out.println("semantic To Be Forgotten");
        updateTimes(toBeForgettingClassSemantic,SemanticToBeForgotSet,SCORE_PROP_TIMES_FORGOTTEN);

        //check the element low score and increment the data property
        Set<String> lowScoreEpisodicSet= new HashSet<String>();
        Set<String> lowScoreSemanticSet= new HashSet<String>();
        System.out.println("episodic low score");
        updateTimes(lowScoreClassEpisodic,lowScoreEpisodicSet,SCORE_PROP_TIMES_LOW_SCORE);
        System.out.println("semantic low score");
        updateTimes(lowScoreClassSemantic,lowScoreSemanticSet,SCORE_PROP_TIMES_LOW_SCORE);
        System.out.println("updating user decision");
        userNoForget(NAME_EPISODIC,false);
        userNoForget(NAME_SEMANTIC,true);

    }
    public float ValueOfDataPropertyFloat(MORAxioms.DataSemantics dataProperties, String dataPropertyName){
        //for all the input dataproperties
        for (MORAxioms.DataSemantic i:dataProperties){
            //if the dataproperty coincides with the desired one
            if(i.toString().contains(dataPropertyName)){
                //take only the number
                String str = i.toString().replaceAll("[^\\d.]","");
                //return the number as float
                return Float.parseFloat(str.substring(1));
            }

        }
        return ((float)-1.0);
    }
    public void updateTimes(MORFullConcept classUpdated,Set<String> names, String Property){
        classUpdated.readSemantic();
        for(OWLNamedIndividual i:classUpdated.getIndividualClassified()){
            names.add(i.toStringID().substring(IRI_ONTO.length()+1));
        }
         if(names.isEmpty()){
            System.out.println("nothing to update");
            return ;
         }
        for(String s: names){
            MORFullIndividual ind=new MORFullIndividual(s,
                    ONTO_NAME,
                    FILE_PATH,
                    IRI_ONTO);
            ind.readSemantic();
            int number=(int) ValueOfDataPropertyFloat(ind.getDataIndividual(),
                    Property);
            number++;
            ind.removeData(Property);
            ind.addData(Property,number);
            ind.writeSemantic();

            System.out.println(s);
        }


    }
    public void userNoForget(String name, Boolean userDecision){
        MORFullIndividual ind= new MORFullIndividual(name,
                ONTO_NAME,
                FILE_PATH,
                IRI_ONTO);
        ind.readSemantic();
        ind.removeData(SCORE_PROP_USER_NO_FORGET);
        ind.addData(SCORE_PROP_USER_NO_FORGET,userDecision,true);
        ind.writeSemantic();
    }
}
