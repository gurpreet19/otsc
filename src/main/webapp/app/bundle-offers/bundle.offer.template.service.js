(function() {
    'use strict';
    angular
        .module('otscApp')
        .factory('BundleOfferTemplate', BundleOfferTemplate);

    BundleOfferTemplate.$inject = ['$http'];

    function BundleOfferTemplate ($http) {
      var factory = {};

      factory.get_template_schema_form = function(template_file_name) {
        console.log("inside get_template_schema_form BundleOfferTemplate service");

        // schema definition
        var template_schema = {};
        // form definition
        var template_form = [];

        // use angular http get to fetch the YAML template file from the server's
        // "templates" directory

        return $http.get("/content/otsc-templates/bundle-offers/" + template_file_name).then(
          function(data, status, headers, config) {
            console.log("inside http.get.then");
            var template = jsyaml.safeLoad(data.data, 'utf8');
            var template_parameters = template.template_parameters;
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
                  "type":  fields[j].param_type,
                  "format": fields[j].param_format
                };
                if(fields[j].param_enum !== undefined) {
                  field_group[fields[j].param_name].enum = fields[j].param_enum;
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

              console.log(field_group);

              // add the field group to the schema
              template_schema[field_group_name] = {
                "title":       template_parameters[i].desc,
                "type":       "object",
                "properties": field_group
              };
              template_schema[field_group_name].required = required;
            }

            console.log(JSON.stringify(template_schema));
            console.log(JSON.stringify(template_form));

            //cb([template_schema,template_form]);
            return [template_schema, template_form, template_type];
          }
        );
      };
      return factory;
    }
})();
