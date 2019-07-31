# Patrolling-Algorithms
Masters Thesis
**ALL FILES**

Patrolling Algorithms in 2D Space.pdf     // Thesis

SOURCE FILES 

Colors.java
Edge.java
EulerTour.java
Graph.java
Group.java
GroupManager.java
InputGraph.java
KrushkalMST.java
Point.java
PointDistributor.java // Main Class
stdlib                 // Visualisation Library

PROGRAM 

BGTProblem.bat       // Executable with windows 
BGTProblem.jar       // Otherwise 

ADDITIONAL APPENDICES 

Appendices D, E and F.pdf
Appendix G.pdf

The work carried out in this thesis centred around the topic of perpetual scheduling within the field of Computer Science. More precisely, the focus of this project was on scheduling problems relating to the monitoring of virtual machines in a 2D environment. This thesis attempted to examine a precise algorithmic scheduling problem known as the Bamboo Garden Trimming Problem (Gąsieniec et al., 2017). This problem consists of specific instances where each virtual machine has a known and possibly unique urgency factor. Consequently, some virtual machines need to be attended more frequently than others. Gąsieniec et al., (2017) proposed an algorithm in respect to the Bamboo Garden Trimming Problem, along with two natural lower bounds. In this project, we implemented and experimented with this algorithm using a variety of different input instances. The aim of this experimentation was to discover how well the algorithm performed in relation to the applied input. The considered input comprised of two key components, (1) the distribution of virtual machines in 2D space and (2) the urgency factor of these machines. This input was synthetic generated using a specifically designed piece of software for the purpose of this project. This software has the functionality to compute a diverse dataset with the combination of both spatial and weight distributions. This measured input varied from random distributions to a number of more probabilistic distributions. For each instance, the software calculated the two known lower bounds of the algorithm as well an approximate solution to the algorithm. This so-lution was based on the upper bound of the proposed algorithm. During experimentation, we found that the algorithm performed significantly well for power law weight distributions. The de-sign of power law instances utilised the relative change in distance from the centre of the distri-bution in order to assign the proportional weight of points. In this case, those points located closer to the centre were naturally heavier than those further away from the centre. However, the algo-rithm seemed to perform significantly worse for random weight distributions, as these particular instances recorded the poorest averages across all spatial distributions.
