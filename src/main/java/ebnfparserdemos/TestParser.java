/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ebnfparserdemos;

import java.io.PrintStream;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.measure.Unit;

import tech.units.indriya.format.SymbolMap;
import tech.units.indriya.unit.Units;

/**
 *
 */
public class TestParser {

    private static final String BUNDLE_NAME = "tech.units.indriya.format.messages"; //$NON-NLS-1$

    public static void main(String[] args) {
        // No Mix
        runParserNoMix("W/s");
        runParserNoMix("e^(W/s)");
        runParserNoMix("log(W/s)");
        runParserNoMix("ln(W/s)");
        runParserNoMix("3^4*W/s");
        // Exp
        runParserExp("W/s");
        runParserExp("e");
        runParserExp("exp(W/s)");
        runParserExp("log(W/s)");
        runParserExp("ln(W/s)");
        runParserExp("3^4*W/s");
        // Minimal
        runParserMinimal("W/s²");
        runParserMinimal("W/s/s");
        runParserMinimal("W/s^2");
        runParserMinimal("W/(s*s*m)");
        runParserMinimal("e");
        runParserMinimal("e");
        // all fails
        // runParserMinimal("3*W");
        // runParserMinimal("W/s^s");
        // runParserMinimal("exp(W/s)");
        // runParserMinimal("log(W/s)");
        // runParserMinimal("ln(W/s)");
        // runParserMinimal("3^4*W/s");
    }

    static void runParserMinimal(String unitString) {
        try {
            PrintStream utfOut = new PrintStream(System.out, true, "UTF-8");
            utfOut.println("Parsing unit string with Minimal Parser: " + unitString);
            SymbolMap map = SymbolMap.of(ResourceBundle.getBundle(BUNDLE_NAME, Locale.ROOT));
            map.alias(Units.COULOMB.multiply(1.60217662e-19), "e");
            UnitParserMinimal parser = new UnitParserMinimal(map, new java.io.StringReader(unitString));
            Unit<?> unit = parser.parseUnit();
            utfOut.println("\tunit = " + unit);
            utfOut.println("\tdimension = " + unit.getDimension());
        } catch (Throwable e) {
            // Catching Throwable is ugly but JavaCC throws Error objects!
            System.out.println("Syntax check failed: " + e.getMessage());
        }
    }

    static void runParserNoMix(String unitString) {
        try {
            PrintStream utfOut = new PrintStream(System.out, true, "UTF-8");
            utfOut.println("Parsing unit string with NoMix Parser: " + unitString);
            SymbolMap map = SymbolMap.of(ResourceBundle.getBundle(BUNDLE_NAME, Locale.ROOT));
            UnitParserNoMix parser = new UnitParserNoMix(map, new java.io.StringReader(unitString));
            Unit<?> unit = parser.parseUnit();
            utfOut.println("\tunit = " + unit);
            utfOut.println("\tdimension = " + unit.getDimension());
        } catch (Throwable e) {
            // Catching Throwable is ugly but JavaCC throws Error objects!
            System.out.println("Syntax check failed: " + e.getMessage());
        }

    }

    static void runParserExp(String unitString) {
        try {
            PrintStream utfOut = new PrintStream(System.out, true, "UTF-8");
            utfOut.println("Parsing unit string with Exp Parser: " + unitString);
            SymbolMap map = SymbolMap.of(ResourceBundle.getBundle(BUNDLE_NAME, Locale.ROOT));
            map.alias(Units.COULOMB.multiply(1.60217662e-19), "e");
            UnitParserExp parser = new UnitParserExp(map, new java.io.StringReader(unitString));
            Unit<?> unit = parser.parseUnit();
            utfOut.println("\tunit = " + unit);
            utfOut.println("\tdimension = " + unit.getDimension());
        } catch (Throwable e) {
            // Catching Throwable is ugly but JavaCC throws Error objects!
            System.out.println("Syntax check failed: " + e.getMessage());
        }

    }

}
