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

Route::group(['prefix' => '/vragen'], function() {

    Route::get('/',"VragenController@ShowVragen");

    Route::post('/edit',"VragenController@UpdateVraag");
    
    Route::get('/delete/{Question_ID}',"VragenController@DeleteVraag");
    
    Route::post('/add', "VragenController@AddVraag");
    
});

Route::group(['prefix' => '/antwoorden'], function() {
        
    Route::get('/{Question_ID}',"VragenController@ShowAntwoorden");
        
    Route::post('/edit', "VragenController@UpdateAntwoord");
    
    Route::get('/delete/{Answer_ID}',"VragenController@DeleteAntwoord");
    
    Route::post('/add', "VragenController@AddAntwoord");
        
});