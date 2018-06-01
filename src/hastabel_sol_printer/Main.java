package hastabel_sol_printer;

import hastabel.World;
import hastabel.lang.Formula;
import hastabel.lang.Element;
import hastabel.lang.Predicate;
import hastabel.lang.Type;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

import java.util.stream.Collectors;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main
{

   public static void main (final String... args)
   {
      final Map<String, Map<Element, Element>> str_funs;
      final Map<Element, Element> waveforms;
      final Parameters params;
      final World world;

      str_funs = new HashMap<String, Map<Element, Element>>();
      waveforms = new HashMap<Element, Element>();

      System.out.println("#### HaStABeL Solutions Pretty Printer ####");
      System.out.println("# Parsing parameters...");

      params = new Parameters(args);

      if (!params.are_valid())
      {
         return;
      }

      world = new World();

      System.out.println("# Loading model...");

      load_model(world, params);

      if (!world.is_valid())
      {
         System.out.println("# Failure.");

         return;
      }

      /** FIXME: Waveforms are VHDL specific. **/
      System.out.println("# Isolating waveforms & string functions.");
      //isolate_waveforms(world, waveforms);
      //string_functions(world, waveforms, str_funs);

      /* I don't think this is needed */
      //System.out.println("# Modeling graphs in first-order...");
      //world.ensure_first_order();

      for (final String pred_name: params.get_target_predicates())
      {
         final String pp_file;

         pp_file = params.get_pretty_print_file_for(pred_name);

         if (pp_file == null)
         {
            System.err.println
            (
               "[E] Could not find pretty print file for predicate \""
               + pred_name
               + "\"."
            );
         }
         else
         {
            print_solutions_of(world, pred_name, pp_file);
         }
      }
   }

   private static void print_solutions_of
   (
      final World world,
      final String pred_name,
      final String pp_file
   )
   {
      final List<String> naming;
      final Predicate pred;
      final String pp_template;

      pred = world.get_predicates_manager().get(pred_name);
      pp_template = null; //file_to_pp_template(pp_file);

      if (pred == null)
      {
         System.err.println("[E] Could not find predicate \"" + pred + "\".");

         return;
      }

      if (pp_template == null)
      {
         return;
      }

      naming = pred.get_naming();

      for (final List<Element> member: pred.get_members())
      {
         for (int i = (member.size() - 1); i >= 0; i--)
         {
         }
      }
   }

   private static void load_file (final World world, final String filename)
   {
      try
      {
         System.out.println("# Loading \"" + filename + "\"...");
         world.load(filename);
      }
      catch (final IOException ioe)
      {
         System.err.println
         (
            "[E] IOException when loading \""
            + filename
            + "\":\n"
            + ioe.getMessage()
         );

         world.invalidate();
      }
   }

   private static void load_model
   (
      final World world,
      final Parameters params
   )
   {
      for (final String level_file: params.get_level_files())
      {
         load_file(world, level_file);

         if (!world.is_valid())
         {
            return;
         }
      }

      for (final String model_file: params.get_model_files())
      {
         load_file(world, model_file);

         if (!world.is_valid())
         {
            return;
         }
      }
   }

   private static void isolate_waveforms
   (
      final World world,
      final Map<Element, Element> waveforms
   )
   {
      final Predicate is_waveform_of;

      is_waveform_of = world.get_predicates_manager().get("is_waveform_of");
   }
}
