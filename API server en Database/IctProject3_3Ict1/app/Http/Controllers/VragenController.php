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
        return response()-> json($vragen);
        
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
            return response()-> json($vragen);
        } else {
            return response()-> json("error");
        }    
    }

}