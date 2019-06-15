(function() {
    'use strict';
    angular
        .module('otscApp')
        .factory('ServiceTemplate', ServiceTemplate);

    ServiceTemplate.$inject = ['$http'];

    function ServiceTemplate ($http) {
      var factory = {};

      factory.get_template_schema_form = function(template_file_name) {
        console.log("inside get_template_schema_form ServiceTemplate service");

        // schema definition
        var template_schema = {};
        // form definition
        var template_form = [];

        // use angular http get to fetch the YAML template file from the server's
        // "templates" directory

        return $http.get("/content/otsc-templates/services/" + template_file_name).then(
          function(data, status, headers, config) {
            console.log("inside http.get.then");
            var template = jsyaml.safeLoad(data.data, 'utf8');
            var template_parameters = template.template_parameters;
            var service_parameters = template.service_parameters;
            var template_type = template.template_type;

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
                if(fields[j].param_enum_multi !== undefined) {
					field_group[fields[j].param_name].type = "array";
					field_group[fields[j].param_name].items = {};
					field_group[fields[j].param_name].items.enum = fields[j].param_enum_multi;
            	}                
                // fields need to be specified in dot notation as per schema model
                if(fields[j].date_format !== undefined) {
                    template_form.push({
                        "key": field_group_name + "[]." + fields[j].param_name ,"minDate": "1995-09-01","maxDate": new Date(),"format": fields[j].date_format
                    });
                  }
                  else{
                  template_form.push({
                      "key": field_group_name + "[]." + fields[j].param_name
                  });
                }
                if(fields[j].value_required == undefined)
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


            // for service params


            for(var i = 0; i < service_parameters.length; i++) {
              var required = [];
              var defaultValue;
              var field_group_name = service_parameters[i].name;
              // list of fields in the field group, taken from YAML template
              var fields = service_parameters[i].params;
              // field_group holds all the field definitions in a group of fields in
              // "angular json schema" format
              var field_group = {};

              template_form.push({
                  "key": field_group_name,
                  "type": "fieldset"
              });
              // inner loop to iterate over all fields in the field group
              for(var j = 0; j < fields.length; j++) {
            	if(fields[j].value_required == undefined)
                  required.push(fields[j].param_name);
                else defaultValue = '';
                  
                field_group[fields[j].param_name] = {
                  "title": fields[j].param_desc,
                  "type":  fields[j].param_type,
                  "default" : defaultValue
                };
                if(fields[j].param_enum !== undefined) {
                  field_group[fields[j].param_name].enum = fields[j].param_enum;
                }
                if(fields[j].param_enum_multi !== undefined) {
					field_group[fields[j].param_name].type = "array";
					field_group[fields[j].param_name].items = {};
					field_group[fields[j].param_name].items.enum = fields[j].param_enum_multi;
            	}
                // fields need to be specified in dot notation as per schema model
                template_form.push({
                    "key": field_group_name + "[]." + fields[j].param_name
                });

              }
              // add the field group to the schema
              template_schema[field_group_name] = {
                "title":       service_parameters[i].desc,
                "type":       "object",
                "properties": field_group
              };
              template_schema[field_group_name].required = required;
            }

            console.log(JSON.stringify(template_schema));

            //cb([template_schema,template_form]);
            return [template_schema, template_form, template_type];
          }
        );
      };
      return factory;
    }
})();
