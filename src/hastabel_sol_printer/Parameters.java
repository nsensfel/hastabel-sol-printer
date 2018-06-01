package hastabel_sol_printer;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

import java.io.File;

public class Parameters
{
   private final List<String> level_files;
   private final List<String> model_files;
   private final Map<String, String> pretty_print_files;
   private final List<String> target_predicates;
   private final boolean are_valid;

   public static void print_usage ()
   {
      System.out.println
      (
         "HaStABeL to IDP\n"
         + "USAGE:\n"
         + "\tjava -jar hastabel_sol_printer.jar <FILES>+\n"
         + "PARAMETERS:\n"
         + "\t- <FILES>\tList of files to be loaded.\n"
         + "NOTES:\n"
         + "\t- Property files have a \".pro\" extension.\n"
         + "\t- Model files have a \".mod\" extension.\n"
         + "\t- Level files have a \".lvl\" extension.\n"
         + "\t- Pretty Printing template files have a \".pp\" extension.\n"
         + "\t- The files may be given in any order."
         + "\t- Only content relevant to what is in the \".sol.mod\" and "
         + "\".sol.lvl\" files will be printer."
      );
   }

   public Parameters (final String... args)
   {
      boolean has_error;

      level_files = new ArrayList<String>();
      model_files = new ArrayList<String>();
      pretty_print_files = new HashMap<String, String>();
      target_predicates = new ArrayList<String>();

      has_error = false;

      for (int i = 0; i < args.length; ++i)
      {
         if (args[i].endsWith(".sol.mod"))
          {
            target_predicates.add("???" /* TODO: get pred name from filename */);
            model_files.add(args[i]);
         }
         else if (args[i].endsWith(".lvl"))
         {
            level_files.add(args[i]);
         }
         else if (args[i].endsWith(".mod"))
          {
            model_files.add(args[i]);
         }
         else if (args[i].endsWith(".pp"))
         {
            pretty_print_files.put("???", args[i]);
         }
         else
         {
            System.err.println
            (
               "[E] Unknown file type \""
               + args[i]
               + "\"."
            );

            has_error = true;
         }
      }

      are_valid = !has_error;
   }

   public List<String> get_level_files ()
   {
      return level_files;
   }

   public String get_pretty_print_file_for (final String str)
   {
      return pretty_print_files.get(str);
   }

   public List<String> get_model_files ()
   {
      return model_files;
   }

   public List<String> get_target_predicates ()
   {
      return target_predicates;
   }

   public boolean are_valid ()
   {
      return are_valid;
   }
}
