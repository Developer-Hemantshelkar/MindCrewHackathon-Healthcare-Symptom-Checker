<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Symptom Analysis Result</title>
    <style>
        body {
            background-image: url('HC.png');
            background-size: cover;
            font-family: Arial, sans-serif;
            text-align: center;
            color: white;
        }
        .result-box {
            margin: 100px auto;
            width: 500px;
            padding: 20px;
            background: rgba(0,0,100,0.6);
            border-radius: 15px;
            box-shadow: 10px 10px 20px black;
        }
        .b1 {
            background: linear-gradient(blue, white);
            color: white;
            padding: 10px 20px;
            border-radius: 20px 0px 20px 0px;
            border: none;
            cursor: pointer;
            margin-top: 20px;
        }
        .b1:hover {
            background: linear-gradient(white, blue);
            color: black;
        }
    </style>
</head>
<body>
    <div class="result-box">
        <h2>Your Symptoms:</h2>
        <p><%= request.getAttribute("symptoms") %></p>

        <h2>Suggested Next Steps:</h2>
        <p><%= request.getAttribute("suggestion") %></p>

        <a href="SymptomCheck.html"><button class="b1">Check Again</button></a>
    </div>
</body>
</html>
