Mini juego AskMe "Adivina mi transporte"

Modo Juego :

*La lógica del juego esta basada en el funcionamiento básico de un arbol binario, no balanceado.
Para la toma de deciciones en el juego consta de un interfaz grafica en JavaFX que lo simplifica a dos botone "Si o No"

El juego se lleva acabo por rondas de adivinaza.
En cada ronda el usuario deberá responder a las pregntas que la aplicacion plantea.
La aplicacion llegara a un limite de preguntas y entonces preguntará si la opcion a la que ha llegado es correcta o no.

*En caso de que la opcion no sea correcta, el jugo tiene la capacidad de aprender nuevas rutas o preguntas.
	Al finalizar cada juego en el caso de no haber acertado el juego serializa el arbol binario para no perder la información de las partidas previas.
El archivo de salida que contine los datos del juego se llama "ArbolDatos.bin"

*En caso contrario el juego finaliza y espera a una nueva ronda al presionar iniciat juego.

:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

Modo Depuración:

En la depuración se despliga una visualizacion de el numero de nodos la altura del arbol y El esquema del arbol.

Puede ejecutar el juego y la depuracion al mismo tiempo.
El boton actualizar permite cargar los datos del arbol nuevamente en caso de que se agregaran mas datos despues de que se desplego la ventana de depuración.
Ademas cuenta con la opciion de eliminar los datos del arbol.
En ese caso el juego se reiniciara.

Universidad Politécnica de Chiapas.
author: Elihu Cruz Albores.
