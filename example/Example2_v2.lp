\ENCODING=ISO-8859-1
\Problem name: ilog.cplex

Maximize
 obj: x2
Subject To
 c1: - x1 + x2 <= 1
 c3: 3 x1 + 2 x2 <= 2
 c5: 2 x1 + 3 x2 <= 12
Bounds
 0 <= x1 <= 50000
 0 <= x2 <= 60000
End
