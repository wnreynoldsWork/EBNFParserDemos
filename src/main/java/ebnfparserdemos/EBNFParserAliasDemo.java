/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ebnfparserdemos;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.measure.Unit;

import tech.units.indriya.format.SymbolMap;
import tech.units.indriya.internal.format.UnitFormatParser;
import tech.units.indriya.unit.Units;

/**
 *
 */
public class EBNFParserAliasDemo {
    private static final String BUNDLE_NAME = "tech.units.indriya.format.messages"; //$NON-NLS-1$

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            // Note that in stock ebnf parser, 'e' is disallowed, since it is used for
            // exponentiating units.
            String electronString = "electron";
            String footString = "foot";

            SymbolMap map = SymbolMap.of(ResourceBundle.getBundle(BUNDLE_NAME, Locale.ROOT));
            map.alias(Units.COULOMB.multiply(1.60217662e-19), electronString);
            map.alias(Units.METRE.multiply(0.3048), footString);

            Unit<?> electronUnit = parse(electronString, map);
            printUnit(electronString, electronUnit);

            Unit<?> footUnit = parse(footString, map);
            printUnit(footString, footUnit);

            Unit<?> badUnit = parse("badUnit", map);
            printUnit("badUnit", badUnit);

        } catch (Throwable e) {
            // Catching Throwable is ugly but JavaCC throws Error objects!
            System.out.println("Syntax check failed: " + e.getMessage());
        }
    }

    static void printUnit(String unitString, Unit<?> unit) throws UnsupportedEncodingException {
            PrintStream utfOut = new PrintStream(System.out, true, "UTF-8");
            utfOut.println("Parsing unit string with NoMix Parser: " + unitString);
            utfOut.println("\tunit = " + unit);
            utfOut.println("\tdimension = " + unit.getDimension());
    }

    static Unit<?> parse(String unitString, SymbolMap map) {
        UnitFormatParser parser = new UnitFormatParser(map, new java.io.StringReader(unitString));
        return parser.parseUnit();
    }

}
