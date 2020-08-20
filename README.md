# Arsw-Lab2
## Part I - Before finishing class

Thread control with wait/notify. Producer/consumer

Check the operation of the program and run it. While this occurs, run jVisualVM and check the CPU consumption of the corresponding process. Why is this consumption? Which is the responsible class?

Este consumo es debido a que las clases de Producer y Consumer están constantemente produciendo y consumiendo productos dentro de un ciclo infinito (while true).

La mayor responsabilidad del consumo de CPU es de parte del Consumidor debido a que siempre está consumiendo sin importar si hay o no productos. Por otra parte , la clase Producer tiene una pausa por cada cierto productos producidos que puede aliviar el consumo de CPU.

![](img/cpu.PNG)
