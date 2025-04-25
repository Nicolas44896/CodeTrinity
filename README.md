# 🃏 Piramyd

Piramyd es una implementación digital del clásico juego de cartas solitario "Pyramid", desarrollado en Java como parte de un proceso de refactorización y extensión basado en un repositorio previo en C++, para el proyecto final de la asignatura de Ingeniería de Software por el grupo CodeTrinity.
La extensión se centra en el agregado de cartas especiales, las cuales están diseñadas como una funcionalidad opcional para el usuario, permitiendo activar o desactivar su uso al comienzo de cada partida. El objetivo es ofrecer una experiencia de juego más variada, adaptable tanto para jugadores casuales como para quienes busquen un mayor desafío.

---

## 📌 Descripción del Juego

Piramyd es un juego de cartas para un solo jugador. El objetivo es eliminar todas las cartas de una pirámide emparejando dos cartas que sumen 13 puntos. El juego combina estrategia, memoria y suerte.

---

## 🎮 Reglas del Juego

- Se utiliza una baraja estándar de 52 cartas.
- Las cartas se disponen en forma de pirámide con 7 filas (28 cartas en total).
- Solo se pueden seleccionar cartas que no estén cubiertas por otras.
- Las combinaciones válidas son aquellas que sumen exactamente 13 puntos.
    - K = 13 (se elimina sola)
    - Q (12) + A (1)
    - J (11) + 2
    - 10 + 3, 9 + 4, 8 + 5, 7 + 6
- El resto de la baraja queda en el mazo auxiliar, que puede usarse para encontrar combinaciones adicionales.
- El juego finaliza cuando se elimina toda la pirámide o no quedan más movimientos posibles.

---

