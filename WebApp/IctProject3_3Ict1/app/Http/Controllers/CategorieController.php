<?php

namespace App\Http\Controllers;

use Illuminate\Support\Facades\DB;
use App\CategoryModel;
use App\QuestionModel;
use App\AnswerModel;

use Illuminate\Http\Request;

class CategorieController extends Controller
{
    function index(){

    }
    
    function GetCategorieën(){
        $categorieën = CategoryModel::all();
        return $categorieën; 
    }
    
    function UpdateCategorie(Request $request){
        $categorie = CategoryModel::find($request->id);
        $categorie["Name"] = $request->name;
        $categorie->save();
        return back();
    }
    
    function DeleteCategorie($Category_ID){
        $categorie = CategoryModel::find($Category_ID);
        $categorie->delete();
        return back();
    }
    
    function AddCategorie(Request $request){
        $categorie = new CategoryModel;
        $categorie["Name"] = $request->name;
        $categorie->save();
        return back();
    }
    
    function ShowCategorieën(){
        $categorieën=$this->GetCategorieën();
        return view("categorieTabel",["categorieën"=>$categorieën]);
    }
}
