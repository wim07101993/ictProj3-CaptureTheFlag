@extends('layouts.app')

@section('content')
<body class="grey lighten-3">
    <div class="row">
    </div>
    <div class="container">
        <div class="col s12 m4 l8">
            <form class="form-horizontal" method="POST" action="{{ route('login') }}">
            {{ csrf_field() }}
            <div class="card" style="top:auto;left:auto;right:auto">
                <div class="card-content">
                    <span class="card-title">Aanmelden</span>
                    

                    <div class="form-group{{ $errors->has('email') ? ' has-error' : '' }}">
                        <label for="email" class="col-md-4 control-label">E-Mail Address</label>
                        <input id="email" type="email" class="form-control" name="email" value="{{ old('email') }}" required autofocus>
                        @if ($errors->has('email'))
                            <span class="help-block">
                            <strong>{{ $errors->first('email') }}</strong>
                            </span>
                        @endif
                    </div>
                    <div class="form-group{{ $errors->has('password') ? ' has-error' : '' }}">
                        <label for="password" class="col-md-4 control-label">Password</label>

                        <input id="password" type="password" class="form-control" name="password" required>
                        @if ($errors->has('password'))
                            <span class="help-block">
                            <strong>{{ $errors->first('password') }}</strong>
                            </span>
                        @endif
                    </div>
                    <input type="checkbox" id="remember" name="remember" class="right filled-in green accent-3" {{ old('remember') ? 'checked' : '' }}>
                    <label for="remember">Remember Me</label>
                        <button type="submit" class="right btn btn-primary green accent-3">
                            Login
                        </button>

                </div>
            </div>
            </form>
        </div>
    </div>
</body>
            
@endsection
