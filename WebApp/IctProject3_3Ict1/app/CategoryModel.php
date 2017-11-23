<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class CategoryModel extends Model 
{
    protected $connection = 'mysql';
    
    protected $fillable = [
        'Category_ID', 'Name'
    ];
    
    protected $primaryKey = "Category_ID";
    
    protected $table = "category";
    
    public $timestamps = false;

    public function questions()
    {
        return $this->belongsToMany('App\QuestionModel', 'question_per_category', 'Category_ID', 'Question_ID');
    }
}