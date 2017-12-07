<?php

namespace App\Http\Controllers;

use Illuminate\Support\Facades\DB;
use App\CategoryModel;
use App\QuestionModel;
use App\AnswerModel;

use Illuminate\Http\Request;

class VragenController extends Controller
{
    function index(){

    }
    
    function GetVragen(){
        $vragen = QuestionModel::all();
        foreach($vragen as $vraag){
            $vraag["Answers"] = QuestionModel::find($vraag["Question_ID"])->answers;
        }
        return $vragen; 
    }
    
    function GetVragenEnCategoriëen(){
        $vragen = QuestionModel::all();
        foreach($vragen as $vraag){
            $vraag["Answers"] = QuestionModel::find($vraag["Question_ID"])->answers;
            $vraag["Categories"] = QuestionModel::find($vraag["Question_ID"])->categories;
        }
        return $vragen; 
    }

    function GetAntwoorden($Question_ID){
        $antwoorden = QuestionModel::find($Question_ID)->answers;
        return $antwoorden;
    }
    
    function UpdateVraag(Request $request){
        $vraag = QuestionModel::find($request->id);
        $vraag["Question"] = $request->question;
        $vraag->save();
        return back();
    }
    
    function UpdateAntwoord(Request $request){
        $antwoord = AnswerModel::find($request->id);
        $antwoord["Answer"] = $request->answer;
        if($request->correct != null){
            $antwoord["Correct"] = 1;
        } else {
            $antwoord["Correct"] = 0;
        }
        $antwoord->save();
        return back();
    }
    
    function DeleteVraag($Question_ID){
        $vraag = QuestionModel::find($Question_ID);
        $vraag->delete();
        return back();
    }
    
    function DeleteAntwoord($Answer_ID){
        $antwoord = AnswerModel::find($Answer_ID);
        $antwoord->delete();
        return back();
    }
    
    function AddVraag(Request $request){
        $vraag = new QuestionModel;
        $vraag["Question"] = $request->question;
        $vraag->save();
        return back();
    }
    
    function AddAntwoord(Request $request){
        $antwoord = new AnswerModel;
        $antwoord["Answer"] = $request->answer;
        $antwoord["Question_ID"] = $request->question_id;
        if($request->correct != null){
            $antwoord["Correct"] = 1;
        } else {
            $antwoord["Correct"] = 0;
        }
        $antwoord->save();
        return back();
    }

    function GetRandomVragen($Category_ID, $aantal) {
        $alleVragen = CategoryModel::find($Category_ID)->questions;
        if(count($alleVragen) >= $aantal){
            $vragen = [];
            while(count($vragen) < $aantal){
                $vragen[count($vragen)] = rand(1, count($alleVragen));
                $vragen = array_unique($vragen);
            }
            for($index = 0; $index < count($vragen); $index ++){
                $vragen[$index] = QuestionModel::find($vragen[$index]);
                $vragen[$index]["Answers"] = QuestionModel::find($vragen[$index]["Question_ID"])->answers;
            }
            return $vragen;
        } else {
            return "error";
        }    
    }
    
    function ShowVragen(){
        $vragen=$this->GetVragenEnCategoriëen();
        return view("vragenTabel",["vragen"=>$vragen]);
    }
    
    function ShowAntwoorden($Question_ID){
        $vraag= QuestionModel::find($Question_ID);
        $antwoorden=$this->GetAntwoorden($Question_ID);
        return view("antwoordenTabel",["vraag"=>$vraag, "antwoorden"=>$antwoorden, "question_id"=>$Question_ID]);         
    }


}