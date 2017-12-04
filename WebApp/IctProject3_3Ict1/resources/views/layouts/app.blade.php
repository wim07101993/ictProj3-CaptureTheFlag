<!DOCTYPE html>
<html lang="{{ app()->getLocale() }}">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"></script>
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

    <!-- CSRF Token -->
    <meta name="csrf-token" content="{{ csrf_token() }}">
    <style>
    #search:focus{
        text-align: center;
    margin: 0;
    border: 0;
    border-radius: 2px;
    color: black;
    box-shadow: 1px 1px 10px 0px #00000080;
    -webkit-transition: all 0.5s ease-in;
    -moz-transition: all 0.5s ease-in;
    transition: all 0.5s ease-in;
    background-color: #fafafa !important;
    }
    #search{
        text-align: center;
    margin: 0;
    border: 0;
    border-radius: 2px;
    color: white;
    background-color: #9e9e9e !important;
    box-shadow: 0px 0px 2px 0px #00000099;
    }
    </style>
</head>
<body>
        <nav class="grey">
            <div class="nav-wrapper">
                <ul class="left">
                @guest

                @else
                    <li id="categorieënTab"><a href="{{ url('/categorieën') }}">Categorieën</a></li>
                    <li id="vragenTab"><a href="{{ url('/vragen') }}">Vragen</a></li>
                @endguest
                </ul>
                <ul class="right">
                @guest
                    
                @else
                    <li>
                        <a class="waves-effect waves-light btn grey lighten-5" style="color:black;" href="{{ route('logout') }}"
                            onclick="event.preventDefault();
                                        document.getElementById('logout-form').submit();">
                                Logout
                        </a>
                        <form id="logout-form" action="{{ route('logout') }}" method="POST" style="display: none;">
                            {{ csrf_field() }}
                        </form>
                    </li>
                @endguest
                </ul>
            </div>
        </nav>
        @yield('content')

    <!-- Scripts -->
    <script src="{{ asset('js/app.js') }}"></script>
</body>
</html>
