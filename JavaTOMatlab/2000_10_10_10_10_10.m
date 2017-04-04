%------------------------------------------------------------------------%
% This file generates a chart with the position of centroids,            %
% Node B, RNC, MSC and SGSN has the software                             %
% MATLAB.                                                                %
%                                                                        %
% To generate the graph, enter the MATLAB program. Then                  % 
% Enter the file name without the extension.                             %
% For example:                                                           %
% Matlab >> Filename                                                     %
%                                                                        %
%                                                                        %
%------------------------------------------------------------------------%


CentroideX = [1830 40 290 1343 987 331 577 402 1603 1185 ];
CentroideY = [1717 159 869 705 1308 852 1344 1535 675 134 ];
NoeudBx = [1002 843 97 1867 1864 1216 1513 1378 1396 1915 ];
NoeudBy = [1393 696 205 1435 310 194 1701 442 615 1797 ];
RNCx = [501 379 963 98 566 677 54 615 75 1937 ];
RNCy = [289 104 433 499 310 1920 1519 150 834 1938 ];
MSCx = [1143 484 1204 1096 827 748 1651 135 262 1463 ];
MSCy = [1504 655 1861 599 1011 1680 1478 965 232 828 ];
SGSNx = [541 1099 10 1095 898 726 886 1047 1421 1392 ];
SGSNy = [139 594 1713 436 1383 392 1209 441 494 247 ];
a = plot(CentroideX, CentroideY, 'x', NoeudBx, NoeudBy, '^', RNCx, RNCy, 'o',MSCx, MSCy, 'd', SGSNx, SGSNy, 's');
axis([0 2000 0 2000]);
orient landscape;
grid on;
title ('Graphique des sites potentiels');
xlabel ('Distance (m)');
ylabel ('Distance (m)');
legend(a, 'TP','Node B','RNC','MSC','SGSN', -1);
print 2000_10_10_10_10_10.ps;
