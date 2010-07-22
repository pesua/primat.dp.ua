<script type="text/javascript">
$(document).ready(function(){
  $("#school-school").autocomplete("/html/portlet/schoolfield/getschool.jsp");

  $("#school-country").autocomplete("/html/portlet/schoolfield/getcountry.jsp");

  $("#school-city").autocomplete("/html/portlet/schoolfield/getcity.jsp");
});
</script>