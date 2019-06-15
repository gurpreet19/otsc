(function() {
    'use strict';
    angular
        .module('otscApp')
        .factory('ResourceTemplate', ResourceTemplate);

    ResourceTemplate.$inject = ['$http'];

    function ResourceTemplate ($http) {
      var factory = {};

      factory.get_template_schema_form = function(template_file_name) {
        console.log("inside get_template_schema_form ResourceTemplate service");

        // schema definition
        var template_schema = {};
        // form definition
        var template_form = [];

        // use angular http get to fetch the YAML template file from the server's
        // "templates" directory

        return $http.get("/content/otsc-templates/resources/" + template_file_name).then(
          function(data, status, headers, config) {
            console.log("inside http.get.then");
            var template = jsyaml.safeLoad(data.data, 'utf8');
            var template_parameters = template.template_parameters;

            // outer loop to iterate over all field groups
            for(var i = 0; i < template_parameters.length; i++) {
              var required = [];
              var field_group_name = template_parameters[i].name;
              // list of fields in the field group, taken from YAML template
              var fields = template_parameters[i].params;
              // field_group holds all the field definitions in a group of fields in
              // "angular json schema" format
              var field_group = {};

              template_form.push({ 
                  "key": field_group_name,
                  "type": "fieldset"
              });
              // inner loop to iterate over all fields in the field group
              for(var j = 0; j < fields.length; j++) {
                field_group[fields[j].param_name] = {
                  "title": fields[j].param_desc,
                  "type":  fields[j].param_type
                };
                if(fields[j].param_enum !== undefined) {
                  field_group[fields[j].param_name].enum = fields[j].param_enum;
                }
                // fields need to be specified in dot notation as per schema model
                template_form.push({ 
                    "key": field_group_name + "[]." + fields[j].param_name
                });
                required.push(fields[j].param_name);
              }
              // add the field group to the schema
              template_schema[field_group_name] = {
                "title":       template_parameters[i].desc,
                "type":       "object",
                "properties": field_group
              };
              template_schema[field_group_name].required = required;
            }

            console.log(JSON.stringify(template_schema));

            //cb([template_schema,template_form]);
            return [template_schema, template_form];
          }
        );
      };
      return factory;
    }
})();
