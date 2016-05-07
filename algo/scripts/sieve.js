(function() {
    var _example = document.getElementById("example");
    var _size = document.getElementById("n-size");

    var _divs = [];
    var A = [];

    var calculatePrimes = function() {
        var n = _size.value;
        _example.innerHTML = "";

        var sn = Math.sqrt(n);
        for(var i = 1; i <= n; i++) A.push(true);
        for(var i = 2; i <= sn; i++) {
            if(A[i] == true) {
                for(var j = 2 * i; j <= n; j += i) A[j] = false;
            }
        }

        for(var i = 1; i <= n; i++) {
            var _div = document.createElement("div");
            if(A[i] == true)
                _div.setAttribute('class', 'prime');
            else
                _div.setAttribute('class', 'disabled');
            _div.innerHTML = "" + i;
            _example.appendChild(_div);
            _divs.push(_div);

        }

    };
    window.onload = function() {
        _example = document.getElementById("example");
        _size = document.getElementById("n-size");
        _size.oninput = calculatePrimes;
        calculatePrimes();
    }

})();
