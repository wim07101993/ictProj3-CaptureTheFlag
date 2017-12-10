<?php

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

Route::get('/',"VragenController@ShowVragen")->middleware('auth');

Route::group(['prefix' => '/vragen'], function() {

    Route::get('/',"VragenController@ShowVragen")->middleware('auth');

    Route::post('/edit',"VragenController@UpdateVraag")->middleware('auth');
    
    Route::get('/delete/{Question_ID}',"VragenController@DeleteVraag")->middleware('auth');
    
    Route::post('/add', "VragenController@AddVraag")->middleware('auth');

    Route::post('/categorieën', "VragenController@UpdateCategorieën")->middleware('auth');
    
});

Route::group(['prefix' => '/antwoorden'], function() {
        
    Route::get('/{Question_ID}',"VragenController@ShowAntwoorden")->middleware('auth');
        
    Route::post('/edit', "VragenController@UpdateAntwoord")->middleware('auth');
    
    Route::get('/delete/{Answer_ID}',"VragenController@DeleteAntwoord")->middleware('auth');
    
    Route::post('/add', "VragenController@AddAntwoord")->middleware('auth');
        
});

Route::group(['prefix' => '/categorieën'], function() {
        
    Route::get('/',"CategorieController@ShowCategorieën")->middleware('auth');
        
    Route::post('/edit', "CategorieController@UpdateCategorie")->middleware('auth');
    
    Route::get('/delete/{Category_ID}',"CategorieController@DeleteCategorie")->middleware('auth');
    
    Route::post('/add', "CategorieController@AddCategorie")->middleware('auth');
        
});

Auth::routes();
