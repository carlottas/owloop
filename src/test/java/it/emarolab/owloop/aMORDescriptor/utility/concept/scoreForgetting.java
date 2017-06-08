package it.emarolab.owloop.aMORDescriptor.utility.concept;
import it.emarolab.amor.owlInterface.OWLReferences;
import it.emarolab.amor.owlInterface.OWLReferencesInterface;
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
    private static MORFullIndividual score;
    private static MORFullIndividual totalScore;
    private static MORFullIndividual scoreEpisodic;
    private static MORFullIndividual totalScoreEpisodic;
    private static MORFullConcept forgotClassSemantic;
    private static MORFullConcept toBeForgettingClassSemantic;
    private static MORFullConcept lowScoreClassSemantic;
    private static MORFullConcept forgotClassEpisodic;
    private static MORFullConcept toBeForgettingClassEpisodic;
    private static MORFullConcept lowScoreClassEpisodic;
    private static OWLReferences onto;
    //string with ontology name of instances
    String ONTO_NAME="score-ontology";
    String FILE_PATH="src/test/resources/carlotta/score_example.owl";
    String IRI_ONTO="http://www.semanticweb.org/carlotta-sartore/scoreOntology";

    String SCORE_PROP_TIMES_FORGOTTEN="TimesForgotten";
    String SCORE_PROP_TIMES_LOW_SCORE="TimesLowScore";
    String SCORE_PROP_USER_NO_FORGET="UserNoForget";
    String SCORE_PROP_HAS_VALUE="hasValue";
    String SCORE_PROP_NUMBER_BELONGING_INDIVIDUAL="NumberBelongingIndividual";
    String SCORE_PROP_NUMBER_EPISODIC_RETRIEVAL="NumberEpisodicRetrieval";
    String SCORE_PROP_NUMBER_RETRIEVAL="NumberRetrieval";
    String SCORE_PROP_NUMBER_SEMANTIC_RETRIEVAL="NumberSemanticRetrieval";
    String SCORE_PROP_NUMBER_SUB_CLASSES="NumberSubClasses";
    String SCORE_PROP_SCORE_BELONGING_INDIVIDUAL="ScoreBelongingIndividual";
    String SCORE_PROP_SCORE_SUB_CLASSES="ScoreSubClasses";
    String SCORE_PROP_HAS_SCORE="hasScore";

    //Classes

    String SCORE_CLASS_EPISODIC_LOW_SCORE="EpisodicScoreLow";
    String SCORE_CLASS_SEMANTIC_LOW_SCORE="SemanticScoreLow";

    String SCORE_CLASS_EPISODIC_TO_BE_FORGOTTEN="EpisodicToBeForgotten";
    String SCORE_CLASS_SEMANTIC_TO_BE_FORGOTTEN="SemanticToBeForgotten";
    String SCORE_CLASS_FORGOTTEN_EPISODIC="EpisodicForgotten";
    String SCORE_CLASS_FORGOTTEN_SEMANTIC="SemanticForgotten";
    //individuals
    String SCORE_OBJ_PROP_FIRST_SUPERCLASS="firstSuperClass";
    String SCORE_OBJ_PROP_IS_FIRST_SUPER_CLASS_OF="isFirstSuperClassOf";

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
    //TODO check the swrl ruels something is not working there
    @Before // called a before every @Test
    //set up of all the variables
    public void setup() {
        //set up of the score
        score = new MORFullIndividual(NAME_SEMANTIC,
                "score-ontology",
                "src/test/resources/carlotta/score-ontology.owl",
                "http://www.semanticweb.org/carlotta-sartore/scoreOntology");
        //set up of the total score
        totalScore=new MORFullIndividual(SCORE_INDIVIDUAL_TOTAL_SEMANTIC,
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
        onto =  OWLReferencesInterface.OWLReferencesContainer.newOWLReferenceFromFileWithPellet(
                ONTO_NAME, FILE_PATH, IRI_ONTO, true);
    }
    @AfterClass // called after all @Test-s
    public static void save() throws Exception{
        forgotClassSemantic.saveOntology("src/test/resources/carlotta/score_example.owl" );
    }
    @Test
    public void forgetting(){
        // check the forgotten element:
        Set<String> EpisodicForgottenSet=new HashSet<String>();
        Set<String> SemanticForgottenSet=new HashSet<String>();
        //semantic element forgotten
        forgotClassSemantic.readSemantic();
        MORAxioms.Individuals forgottenSemantic=forgotClassSemantic.getIndividualClassified();
        for (OWLNamedIndividual i:forgottenSemantic){
            SemanticForgottenSet.add(i.toStringID().substring(IRI_ONTO.length() + 1));
        }
        deleteSemantic(SemanticForgottenSet);
        //episodic elements forgotten
        forgotClassEpisodic.readSemantic();
        MORAxioms.Individuals forgottenEpisodic=forgotClassEpisodic.getIndividualClassified();
        for (OWLNamedIndividual i:forgottenEpisodic){

            EpisodicForgottenSet.add(i.toStringID().substring(IRI_ONTO.length() + 1));
        }
        deleteEpisodic(EpisodicForgottenSet);

        System.out.println("episodic forgot:\n");
        for (String s:EpisodicForgottenSet){
            System.out.println(s);
        }
        System.out.println("semantic forgot\n:");
        for (String s:SemanticForgottenSet){
            System.out.println(s);
        }


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
    public void objectProperty(MORAxioms.ObjectSemantics objProp,String property,Set<String> individuals){
        for (MORAxioms.ObjectSemantic obj : objProp) {
            if (obj.toString().contains(property)) {
                MORAxioms.Individuals ind = obj.getValues();
                for (OWLNamedIndividual i : ind) {
                    //add to the string the new score
                    individuals.add(i.toStringID().substring(IRI_ONTO.length() + 1));
                }

            }
        }
    }
    // can check the sit they should have done it there
    public void deleteSemantic(Set<String> names){
        for (String s:names) {
            MORFullIndividual ind = new MORFullIndividual(s,
                    ONTO_NAME,
                    FILE_PATH,
                    IRI_ONTO);
            ind.readSemantic();
            Set<String> superClass= new HashSet<String>();
            Set<String> belongingIndividual = new HashSet<String>();
            //INDIVIDUAL NOW BELONG  TO THE FIRST SUPERCLASS
            objectProperty(ind.getObjectIndividual(),SCORE_OBJ_PROP_FIRST_SUPERCLASS,superClass);

            objectProperty(ind.getObjectIndividual(),SCORE_OBJ_PROP_HAS_INDIVIDUAL,belongingIndividual);
            if(superClass.isEmpty()){
                //delete its episodic Item
                deleteEpisodic(belongingIndividual);
                //remove first superClass attribute from its subclass
                Set<String> firstSuperClass= new HashSet<String>();
                objectProperty(ind.getObjectIndividual(),SCORE_OBJ_PROP_IS_FIRST_SUPER_CLASS_OF,firstSuperClass);
                for(String sub:firstSuperClass){
                    MORFullIndividual subCl=new MORFullIndividual(sub,
                            ONTO_NAME,
                            FILE_PATH,
                            IRI_ONTO);
                    subCl.readSemantic();
                    subCl.removeObject(SCORE_OBJ_PROP_FIRST_SUPERCLASS,s);
                    subCl.writeSemantic();

                }
            }
            else {
                //Hyp it is unique
                //All its individual will belong to the first superclass
                for (String i : belongingIndividual) {
                    MORFullIndividual individual = new MORFullIndividual(i,
                            ONTO_NAME,
                            FILE_PATH,
                            IRI_ONTO
                    );
                    individual.readSemantic();
                    individual.removeObject(SCORE_OBJ_PROP_IS_INDIVIDUAL_OF);
                    for (String sup : superClass) {
                        individual.addObject(SCORE_OBJ_PROP_IS_INDIVIDUAL_OF, sup);
                    }
                    individual.writeSemantic();
                }

                //REMOVE THE SCORE AND THE CLASS TO THE OTHER CLASSES
                Set<String> superClasses= new HashSet<String>();
                objectProperty(ind.getObjectIndividual(),SCORE_OBJ_PROP_IS_SUB_CLASS_OF,superClasses);
                for (String sup:superClasses){
                    MORFullIndividual superCl=new MORFullIndividual(sup,
                            ONTO_NAME,
                            FILE_PATH,
                            IRI_ONTO);
                    superCl.readSemantic();
                    superCl.removeObject(SCORE_OBJ_PROP_IS_SUPER_CLASS_OF,s);
                    int numberSubClasses=(int) ValueOfDataPropertyFloat(superCl.getDataIndividual(),SCORE_PROP_NUMBER_SUB_CLASSES);
                    numberSubClasses--;
                    float scoreSubClasses=ValueOfDataPropertyFloat(superCl.getDataIndividual(),SCORE_PROP_SCORE_SUB_CLASSES);
                    scoreSubClasses-=ValueOfDataPropertyFloat(ind.getDataIndividual(),SCORE_PROP_HAS_SCORE);
                    RemoveFromTotalSemanticScore(ValueOfDataPropertyFloat(ind.getDataIndividual(),SCORE_PROP_HAS_SCORE));

                    float newScore=(float) computeScore(numberSubClasses,scoreSubClasses,
                            (int) ValueOfDataPropertyFloat(superCl.getDataIndividual(),SCORE_PROP_NUMBER_BELONGING_INDIVIDUAL),
                            ValueOfDataPropertyFloat(superCl.getDataIndividual(),SCORE_PROP_SCORE_BELONGING_INDIVIDUAL),
                            (int) ValueOfDataPropertyFloat(superCl.getDataIndividual(),SCORE_PROP_NUMBER_RETRIEVAL));
                    UpdateSemanticScore(ValueOfDataPropertyFloat(superCl.getDataIndividual(),SCORE_PROP_HAS_SCORE),newScore);

                    superCl.removeData(SCORE_PROP_HAS_SCORE);
                    superCl.addData(SCORE_PROP_HAS_SCORE,newScore);
                    superCl.removeData(SCORE_PROP_NUMBER_SUB_CLASSES);
                    superCl.addData(SCORE_PROP_NUMBER_SUB_CLASSES,numberSubClasses);
                    superCl.removeData(SCORE_PROP_SCORE_SUB_CLASSES);
                    superCl.addData(SCORE_PROP_SCORE_SUB_CLASSES,scoreSubClasses);
                    superCl.writeSemantic();
                }
                Set<String> firstSuperClass= new HashSet<String>();
                objectProperty(ind.getObjectIndividual(),SCORE_OBJ_PROP_IS_FIRST_SUPER_CLASS_OF,firstSuperClass);
                for(String sub:firstSuperClass){
                    MORFullIndividual subCl=new MORFullIndividual(sub,
                            ONTO_NAME,
                            FILE_PATH,
                            IRI_ONTO);
                    subCl.readSemantic();
                    subCl.removeObject(SCORE_OBJ_PROP_FIRST_SUPERCLASS,s);
                    for (String sup: superClass){
                        subCl.addObject(SCORE_OBJ_PROP_FIRST_SUPERCLASS,sup);
                    }
                    subCl.writeSemantic();

                }

                //now that all the individual have been associated to the super class one can delete the individual
                //onto.removeIndividual(s);
               // onto.synchronizeReasoner();
                //System.out.println(s);
            }

        }

    }

    public void deleteEpisodic(Set<String> names){
        for (String name:names){
            MORFullIndividual ind= new MORFullIndividual(name,
                    ONTO_NAME,
                    FILE_PATH,
                    IRI_ONTO);
            ind. readSemantic();
            Set<String> classNames= new HashSet<String>();
            objectProperty(ind.getObjectIndividual(),SCORE_OBJ_PROP_IS_INDIVIDUAL_OF,classNames);
            //removing the property has individual from the corresponding class
            for (String s:classNames){
                MORFullIndividual classBelong= new MORFullIndividual(s,
                        ONTO_NAME,
                        FILE_PATH,
                        IRI_ONTO);
                classBelong.readSemantic();
                classBelong.removeObject(SCORE_OBJ_PROP_HAS_INDIVIDUAL,name);
                classBelong.writeSemantic();
            }
            onto.removeIndividual(name);
            onto.synchronizeReasoner();
        }

    }
    //compute the  score when it is a semantic item
    //inputs :
    //-number of SubClasses
    //-score of subclasses
    //-number of belonging individual
    //-score of belonging individual
    //-number of retrieval
    //output :score
    private double computeScore(int numberSubClasses,float scoreSubClasses,
                                int numberBelongingIndividual,float scoreIndividual,
                                int retrieval){
        //read the current state of total semantic score
        totalScore.readSemantic();
        totalScoreEpisodic.readSemantic();
        //if the value is 0 then it means that it is the first element to be created
        //hence its score is simply equal to 0.4 (number of retrieval*weight)
        if(ValueOfDataPropertyFloat(totalScore.getDataIndividual(),SCORE_PROP_HAS_VALUE)==0){
            return (SEMANTIC_WEIGHT_5*retrieval);
        }
        //if it is not the first item to be created then the score is equal to the weighted sum
        if(ValueOfDataPropertyFloat(totalScoreEpisodic.getDataIndividual(),SCORE_PROP_HAS_VALUE)==0) {
            return (SEMANTIC_WEIGHT_1 * numberBelongingIndividual +
                    SEMANTIC_WEIGHT_2 * scoreIndividual +
                    SEMANTIC_WEIGHT_3 * numberSubClasses +
                    SEMANTIC_WEIGHT_4 * scoreSubClasses / ValueOfDataPropertyFloat(totalScore.getDataIndividual(), SCORE_PROP_HAS_VALUE) +
                    SEMANTIC_WEIGHT_5 * retrieval);
        }
        return  (SEMANTIC_WEIGHT_1 * numberBelongingIndividual +
                SEMANTIC_WEIGHT_2 * scoreIndividual/ValueOfDataPropertyFloat(totalScoreEpisodic.getDataIndividual(),SCORE_PROP_HAS_VALUE) +
                SEMANTIC_WEIGHT_3 * numberSubClasses +
                SEMANTIC_WEIGHT_4 * scoreSubClasses / ValueOfDataPropertyFloat(totalScore.getDataIndividual(), SCORE_PROP_HAS_VALUE) +
                SEMANTIC_WEIGHT_5 * retrieval);
    };

    public void RemoveFromTotalSemanticScore(float score){
        //read the current state of the total semnatic score
        totalScore.readSemantic();
        //reading the data property
        MORAxioms.DataSemantics dataprop=totalScore.getDataIndividual();
        //read the value of hasvalue data property
        float oldTotal=ValueOfDataPropertyFloat(dataprop,SCORE_PROP_HAS_VALUE);
        //change the value by adding the new score
        oldTotal-=score;
        //change the dataproperty value
        totalScore.removeData(SCORE_PROP_HAS_VALUE);
        totalScore.writeSemantic();
        totalScore.addData(SCORE_PROP_HAS_VALUE,oldTotal);
        totalScore.writeSemantic();
        //  assertSemantic();
    }
    public void UpdateSemanticScore(float oldScore, float newScore){
        //read the current state of total semantic item
        totalScore.readSemantic();
        //reading its dataproperties
        MORAxioms.DataSemantics dataprop=totalScore.getDataIndividual();
        //reading the value of hasValue dataproperty
        float total=ValueOfDataPropertyFloat(dataprop,SCORE_PROP_HAS_VALUE);
        //updating the value
        total-=oldScore;
        total+=newScore;
        //updating the data property with the new value just computed
        totalScore.removeData(SCORE_PROP_HAS_VALUE);
        totalScore.writeSemantic();
        totalScore.addData(SCORE_PROP_HAS_VALUE,total);
        totalScore.writeSemantic();
        //assertSemantic();

    }
}
