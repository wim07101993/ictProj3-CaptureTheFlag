<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class AnswerModel extends Model 
{
    protected $connection = 'mysql';
    
    protected $fillable = [
        'Answer_ID', 'Answer', 'Question_ID', 'Correct'
    ];
    
    protected $primaryKey = "Answer_ID";
    
    protected $table = "answer";
    
    public $timestamps = false;
}