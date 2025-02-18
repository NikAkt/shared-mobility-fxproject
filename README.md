# Way Back Home: Gamifying Sustainable Transport Education for Children

**Way Back Home** is an educational, Java-based desktop game that uses gamification to teach children the importance of sustainable transportation and environmental responsibility. This project was developed as part of a Master's group project and is aimed at engaging young players by combining fun gameplay with real-world environmental insights.

## Overview

Rapid urbanization and the reliance on fossil fuels have led to significant environmental challenges. **Way Back Home** challenges players to balance fast, convenient transport options against eco-friendly alternatives. By making sustainable choices, players learn about the real-world impact of transportation on greenhouse gas emissions and climate change.

## Key Features

- **Interactive Gameplay:** Navigate a top-down, grid-based urban map inspired by real-world cities.
- **Environmental Decision Making:** Choose between various transport modes—walking, cycling, buses, and taxis—each with distinct trade-offs between speed and environmental impact.
- **Real-Time Feedback:** A CO₂ gauge provides immediate visual feedback on the environmental cost of your in-game decisions.
- **Educational Pop-Ups:** As you collect in-game items (gems), educational messages appear, offering insights into sustainable mobility and climate change.
- **Multiple Levels:** Experience diverse urban environments with unique challenges in cities like Manhattan, Dublin, and Tokyo.
- **Robust Architecture:** Developed in Java using a Model-View-Controller (MVC) design pattern to ensure modularity and ease of future expansion.

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- An Integrated Development Environment (IDE) for Java (e.g., IntelliJ IDEA, Eclipse)
- Git (to clone the repository)

### Installation

1. **Clone the Repository:**
    ```bash
    git clone https://github.com/NikAkt/shared-mobility-fxproject.git
    cd shared-mobility-fxproject
    ```

2. **Compile the Code:**
    - Open the project in your preferred IDE.
    - Build the project to compile the Java source code.

3. **Run the Game:**
    - Locate the main class (e.g., `Main.java`) and run it to launch the game.

## How to Play

- **Movement:** Use the `W`, `A`, `S`, and `D` keys to move your character around the map.
- **Special Actions:**
  - Press `T` to hail a taxi for a speed boost (with increased CO₂ output).
  - Press `B` to board buses at designated stops.
- **Objective:** Collect a set number of gems while keeping your CO₂ gauge below the critical level and reach the stage’s endpoint before time runs out.

## Project Structure

- **/src:** Contains the Java source code organized by MVC components.
- **/assets:** Graphical assets, including level maps and icons.
- **/docs:** Project documentation, including the report (*Sustainable_Mobility_Game.pdf*).

## Contributing

Contributions are welcome! Please fork the repository, create a feature branch, and submit a pull request with your changes. For major modifications, open an issue to discuss your ideas first.

## Acknowledgments

This game was developed as part of a Master's group project at the School of Computer Science, University College Dublin. Special thanks to all team members for their contributions to advancing sustainable transport education.
