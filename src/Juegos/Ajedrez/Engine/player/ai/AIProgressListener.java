/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Juegos.Ajedrez.Engine.player.ai;

/**
 *
 * @author Juli
 */

// Import the functional interface for AI progress updates
@FunctionalInterface
public interface AIProgressListener {
    void onAIProgress(String progress);
}
