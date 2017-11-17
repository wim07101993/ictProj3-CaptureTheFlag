<?php

use Illuminate\Http\Request;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::group(['prefix' => '/v1'], function() {
    
    Route::group(['prefix' => '/GET'], function() {
        
        //api/v1/GET/Vragen
        Route::get('/Vragen',"VragenController@GetVragen");

        //api/v1/GET/RandomVragen/{Category_ID}/{aantal}
        Route::get('/RandomVragen/{Category_ID}/{aantal}',"VragenController@GetRandomVragen");
        
    });
    
    Route::group(['prefix' => '/POST'], function() {
        
        Route::group(['prefix' => '/create'], function() {
            
            //api/v1/POST/create/Vraag
            Route::post('/Vraag',"VragenController@CreateVraag");          
            
        });
        
        Route::group(['prefix' => '/update'], function() {
            
            //api/v1/POST/update/Vraag/{Question_ID}
            Route::post('/Vraag/{Question_ID}',"VragenController@UpdateVraag");
                
        });
        
    });
    
    Route::group(['prefix' => '/DELETE'], function() {
        
        //api/v1/DELETE/klanten/{Question_ID}
        Route::delete('/Vraag/{Question_ID}',"VragenController@DeleteVraag");
        
    }); 
    
});
