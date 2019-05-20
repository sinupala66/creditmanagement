<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>

<% 
    String amt1=new String();
    String amt2=new String();
    String fromaccno=request.getParameter("acntno");
    String toaccno=request.getParameter("acntno1");
    String amt=request.getParameter("amnt");
     //t t=Integer.parseInt(cr);
    try
               {
        Connection con=null;
        PreparedStatement ps=null;
        PreparedStatement ps1=null;
        
        Class.forName("oracle.jdbc.OracleDriver");
        con=DriverManager.getConnection("Jdbc:Oracle:thin:@localhost:1521:XE","system","sinu");
        Statement st=con.createStatement();
         ResultSet rs=st.executeQuery("select * from uinfo where userid='"+fromaccno+"'");
        while(rs.next())
                   {
            amt1=rs.getString(1);
        }                        
                                        
        String sql="select * from uinfo where userid =' "+toaccno+"  ' ";
        String sql2="update uinfo set cr=? where userid=' "+fromaccno+" ' ";
        String sql3="update uinfo set cr=? where userid=' "+toaccno+" ' ";
        Statement st1=con.createStatement ();
            ResultSet rs1=st1.executeQuery(sql);
         while(rs1.next())
         {
             amt2=rs1.getString(1);
         }                         
         amt1=Integer.toString(Integer.parseInt(amt1)-Integer.parseInt(amt));
          amt2=Integer.toString(Integer.parseInt(amt2)+Integer.parseInt(amt));                       
        ps=con.prepareStatement(sql2);
         ps.setString(1,amt1);
                 
       int i= ps.executeUpdate();
       ps1=con.prepareStatement(sql3);
       ps1.setString(1,amt2);
       int j=ps1.executeUpdate();
       
    if(i>0 && j>0)
    {
          %>
        <script>
            alert("transfer successful");
            window.location="index.jsp";
        </script>
        <%            
    }
    else
    {
        %>
        <script>
            alert("transfer unsuccessful");
            window.location="transfer.jsp";
        </script>
        
        <%
    
     }
           // }}
}
                       catch(Exception e)
    {
            out.println(e);
    }
        
%>