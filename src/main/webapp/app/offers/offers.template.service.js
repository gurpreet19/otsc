(function() {
    'use strict';
    angular
    .module('otscApp')
    .factory('OfferTemplate', OfferTemplate);

    OfferTemplate.$inject = ['$http'];

    function OfferTemplate ($http) {
      var factory = {};
      function bundleSelected(modelValue, form) {
          if(modelValue !== null && modelValue !== undefined) {
            var modelValueStr = JSON.stringify(modelValue);
            console.log("clicked:" + modelValueStr);
          }
      }

      factory.get_template_schema_form_offer = function(template_file_name) {
        console.log("inside step2 ");

        // schema definition
        var template_schema = {};
        // form definition
        var template_form = [];

        // use angular http get to fetch the YAML template file from the server's
        // "templates" directory

        if(template_file_name !== undefined && template_file_name !== null) {
        return $http.get("/content/otsc-templates/offers/" + template_file_name).then(
          function(data, status, headers, config) {
            console.log("inside http.get.then");
            var template = jsyaml.safeLoad(data.data, 'utf8');
            var template_parameters = template.offer_parameters;
            var template_type = template.template_type;

            // outer loop to iterate over all field groups
            for(var i = 0; i < template_parameters.length; i++) {
              var required = [];
              var field_group_name = template_parameters[i].name;
              var field_group_desc = template_parameters[i].description;
              // list of fields in the field group, taken from YAML template
              var fields = template_parameters[i].params;
              // field_group holds all the field definitions in a group of fields in
              // "angular json schema" format
              var field_group = {};

              var temp = {
                  "key": "Plans[]." + field_group_name,
                  "type":"fieldset",
                  "title": field_group_desc
              };
              temp.condition = "model.Plans[arrayIndex].basic_pricing_parameters.bundled_products.indexOf('" + field_group_name + "') > -1";
              template_form.push(temp);
              // inner loop to iterate over all fields in the field group


              for(var j = 0; j < fields.length; j++) {
                console.log(fields[j].param_format);
                field_group[fields[j].param_name] = {
                  "title": fields[j].param_desc,
                  "description": fields[j].param_hint,
                  "type":  fields[j].param_type,
                  "format": fields[j].param_format
                };
                if(fields[j].param_enum !== undefined) {
                  field_group[fields[j].param_name].enum = fields[j].param_enum;
                }
                if(fields[j].param_minimum !== undefined) {
                  field_group[fields[j].param_name].minimum = fields[j].param_minimum;
                }
                if(fields[j].param_enum_multi !== undefined) {
                  field_group[fields[j].param_name].type = "array";
                  field_group[fields[j].param_name].items = {};
                  field_group[fields[j].param_name].items.enum = fields[j].param_enum_multi;
                }
                // fields need to be specified in dot notation as per schema model
                //var formelement = {
                    //"key": "Plans[]." + field_group_name + "[]." + fields[j].param_name
                //};
                //if(fields[j].param_onChange !== undefined) {
                  //formelement.onChange = fields[j].param_onChange;
                //}
                //template_form.push(formelement);
                // prevent boolean fields from being mandatory
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
                if(fields[j].param_type !== "boolean" && fields[j].value_required == undefined) {
                  required.push(fields[j].param_name);
                }
              }
              // add the field group to the schema
              template_schema[field_group_name] = {
                "title":       template_parameters[i].desc,
                "type": "object",
                "properties": field_group
              };
              template_schema[field_group_name].required = required;
            }

            console.log(JSON.stringify(template_schema));

            //cb([template_schema,template_form]);
            return [template_schema, template_form, template_type];
          }
        );
        }
      };

      factory.get_template_schema_form_main = function(template_file_name, step) {
        console.log("inside step1 service");

        // schema definition
        var template_schema = {};
        // form definition
        var template_form = [];

        // use angular http get to fetch the YAML template file from the server's
        // "templates" directory

        if(template_file_name !== undefined && template_file_name !== null) {
        return $http.get("/content/otsc-templates/offers/" + template_file_name).then(
          function(data, status, headers, config) {
            console.log("inside http.get.then");
            var template = jsyaml.safeLoad(data.data, 'utf8');
            var template_parameters = template[step];
            var template_type = template.template_type;

            // outer loop to iterate over all field groups
            for(var i = 0; i < template_parameters.length; i++) {
              var required = [];
              var field_group_name = template_parameters[i].name;
              var field_group_desc = template_parameters[i].description;
              // list of fields in the field group, taken from YAML template
              var fields = template_parameters[i].params;
              // field_group holds all the field definitions in a group of fields in
              // "angular json schema" format
              var field_group = {};

              var temp = {
                  "key": field_group_name,
                  "type": "fieldset",
                  "title": field_group_desc
              };
              if(step === "step2_parameters") {
                temp.key = "Plans[]." + field_group_name;
              }

              template_form.push(temp);
              // inner loop to iterate over all fields in the field group
              for(var j = 0; j < fields.length; j++) {
                field_group[fields[j].param_name] = {
                  "title": fields[j].param_desc,
                  "description": fields[j].param_hint,
                  "type":  fields[j].param_type,
                  "format": fields[j].param_format
                };
                if(fields[j].param_enum !== undefined) {
                  field_group[fields[j].param_name].enum = fields[j].param_enum;
                }
                if(fields[j].param_minimum !== undefined) {
                  field_group[fields[j].param_name].minimum = fields[j].param_minimum;
                }
                if(fields[j].param_enum_multi !== undefined) {
                  field_group[fields[j].param_name].type = "array";
                  field_group[fields[j].param_name].items = {};
                  field_group[fields[j].param_name].items.enum = fields[j].param_enum_multi;
                }
                // fields need to be specified in dot notation as per schema model
                var formelement = {
                    "key": field_group_name + "[]." + fields[j].param_name
                };
                if(step === "step2_parameters") {
                  formelement.key = "Plans[]." + formelement.key;
                }
                if(fields[j].param_onChange !== undefined) {
                  formelement.onChange = fields[j].param_onChange;
                }
                template_form.push(formelement);
                
                // prevent boolean fields from being mandatory
                if(fields[j].param_type !== "boolean" && fields[j].value_required == undefined) {
                  required.push(fields[j].param_name);
                }
              }
              // add the field group to the schema
              template_schema[field_group_name] = {
                "title":template_parameters[i].desc,
                "type": "object",
                "properties": field_group
              };
              template_schema[field_group_name].required = required;
            }

            console.log(JSON.stringify(template_schema));

            //cb([template_schema,template_form]);
            return [template_schema, template_form, template_type];
          }
        );
        }
      };
      return factory;
    }
})();
