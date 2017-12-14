<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class QuestionModel extends Model
{
    protected $connection = 'mysql';
    
    protected $fillable = [
        'Question_ID', 'Question'
    ];
    
    protected $primaryKey = "Question_ID";
    
    protected $table = "question";
    
    public $timestamps = false;

    public function answers()
    {
        return $this->hasMany('App\AnswerModel', 'Question_ID', 'Question_ID');
    }

    public function categories()
    {
        return $this->belongsToMany('App\CategoryModel', 'question_per_category', 'Question_ID', 'Category_ID');
    }
}
