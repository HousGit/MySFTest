package hous.net;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class Client
{
	static String url = "jdbc:mysql://localhost:3306/test";
	static String user = "root";
	static String psd = "hous";
	public static void main(String[] args)// 程序入口
	{

		Client client = new Client();
		client.createFrame();
	}

	private void createFrame()// 主界面
	{

		Frame mFrame = new Frame("           By Hous~");

		Button buttonIn = new Button("插入");
		Button buttonDel = new Button("删除");
		Button buttonQur = new Button("查询");

		Label labelID = new Label("学号");
		Label labelName = new Label("姓名");
		Label labelSex = new Label("性别");
		Label labelIfo = new Label("学生信息");
		Label labelIfo2 = new Label("软件说明");

		TextField textFieldID = new TextField("", 10);
		TextField textFieldName = new TextField("", 10);
		TextField textFieldsex = new TextField("", 10);
		TextArea textAreaShow = new TextArea();
		TextArea textAreaIfo = new TextArea();

		textAreaIfo.setEditable(false);
		// textAreaIfo.setRows(1);
		textAreaIfo.setText("1插入：输入学号、姓名、性别将学生信息插入数据库中。\n"
				+ "2删除：输入要删除的学生学号执行删除。\n" + "3查询：输入任意字段查询相应信息，否则显示数据库中所有学生信息。");

		Panel panel1 = new Panel();
		Panel panel2 = new Panel();
		Panel panel3 = new Panel();

		panel1.add(labelID);
		panel1.add(textFieldID);
		panel1.add(labelName);
		panel1.add(textFieldName);
		panel1.add(labelSex);
		panel1.add(textFieldsex);
		panel1.add(buttonIn);
		panel1.add(buttonDel);
		panel1.add(buttonQur);
		panel2.add(labelIfo2);
		panel2.add(textAreaIfo);
		panel3.add(labelIfo);
		panel3.add(textAreaShow);

		mFrame.add(panel1, BorderLayout.NORTH);
		mFrame.add(panel2, BorderLayout.CENTER);
		mFrame.add(panel3, BorderLayout.SOUTH);
		mFrame.setSize(600, 400);
		mFrame.setLocation(500, 500);
		mFrame.pack();
		mFrame.setVisible(true);

		// TextField点击相应：清零
		textFieldID.addMouseListener(new MouseAdapter()
		{

			@Override
			public void mouseClicked(MouseEvent e)
			{
				textFieldID.setText("");
			}
		});

		textFieldName.addMouseListener(new MouseAdapter()
		{

			@Override
			public void mouseClicked(MouseEvent e)
			{
				textFieldName.setText("");
			}
		});

		textFieldsex.addMouseListener(new MouseAdapter()
		{

			@Override
			public void mouseClicked(MouseEvent e)
			{
				textFieldsex.setText("");
			}
		});
		mFrame.addWindowListener(new WindowAdapter()
		{

			@Override
			public void windowClosing(WindowEvent e)
			{
				mFrame.setVisible(false);
				System.exit(0);
			}
		});

		// buttonIn事件响应
		buttonIn.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (!(Pattern.matches("(\\d{10})", textFieldID.getText())))
				{
					// System.out.print(textFieldID.getText());
					JOptionPane.showMessageDialog(null, "请输入正确的学号（10位数字）！");
					return;
				}
				if (!(Pattern.matches("([\u4e00-\u9fa5]{2,128})",
						textFieldName.getText())))
				{
					// System.out.print(textFieldID.getText());
					JOptionPane.showMessageDialog(null, "请输入正确的中文姓名！");
					return;
				}
				if (!(textFieldsex.getText().equals("男"))
						& !(textFieldsex.getText().equals("女")))
				{
					// System.out.println(textFieldsex.getText());
					JOptionPane.showMessageDialog(null, "请输入正确的性别（男或女）！ ");
					return;
				}

				java.sql.Connection conn = null;
				PreparedStatement pstm = null;
				try
				{
					Class.forName("com.mysql.jdbc.Driver");
					conn = DriverManager.getConnection(
							url, user	,psd);
					pstm = conn
							.prepareStatement("insert into stu value(?, ?, ?);");
					pstm.setString(1, textFieldID.getText());
					pstm.setString(2, textFieldName.getText());
					pstm.setString(3, textFieldsex.getText());
					pstm.executeUpdate();
					textAreaShow.setText("储存成功！" + "学号："
							+ textFieldID.getText() + " 姓名："
							+ textFieldName.getText() + " 性别："
							+ textFieldsex.getText());

				} catch (ClassNotFoundException e1)
				{
					e1.printStackTrace();
				} catch (MySQLIntegrityConstraintViolationException e2)// 主键异常处理
				{
					textAreaShow.setText("存储失败，该条信息已存在！");
				} catch (SQLException e1)
				{
					// System.out.println("sql");
					e1.printStackTrace();
				} finally
				{

					try
					{
						if (pstm != null)
						{
							pstm.close();
						}
						if (conn != null)
						{
							conn.close();
						}

					} catch (SQLException e1)
					{
						e1.printStackTrace();
					}
				}
			}
		});

		buttonDel.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (!(Pattern.matches("(\\d{10})", textFieldID.getText())))
				{
					// System.out.print(textFieldID.getText());
					JOptionPane.showMessageDialog(null, "请输入正确的学号（10位数字）！");
					return;
				}

				java.sql.Connection conn = null;
				PreparedStatement pstm = null;
				try
				{
					Class.forName("com.mysql.jdbc.Driver");
					conn = DriverManager.getConnection(
							url, user, psd);
					pstm = conn
							.prepareStatement("delete  from stu where sID=?");
					pstm.setString(1, textFieldID.getText());
					int i = pstm.executeUpdate();
					if (1 == i)
					{
						textAreaShow.setText("已删除学号为" + textFieldID.getText()
								+ "的信息");
					}
					if (0 == i)
					{
						textAreaShow.setText("删除失败！该条信息不存在。");
					}
					// System.out.println("succeed");
				} catch (ClassNotFoundException e1)
				{
					// System.out.println("notfond");
					e1.printStackTrace();
				} catch (SQLException e1)
				{
					// System.out.println("sql");
					e1.printStackTrace();
				} finally
				{
					try
					{
						if (pstm != null)
						{
							// System.out.println("succeed11");
							pstm.close();
						}
						if (conn != null)
						{
							conn.close();
						}

					} catch (SQLException e1)
					{
						e1.printStackTrace();
					}
				}
			}
		});

		buttonQur.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				// 默认查询所有数据
				if (textFieldID.getText().equals("")
						& textFieldName.getText().equals("")
						& textFieldsex.getText().equals(""))
				{
					java.sql.Connection conn = null;
					Statement stm = null;
					String str = "";
					try
					{
						Class.forName("com.mysql.jdbc.Driver");
						conn = DriverManager.getConnection(
								url, user,psd);
						stm = conn.createStatement();
						ResultSet rs = stm.executeQuery("select * from stu");
						while (rs.next())
						{
							str += "学号：" + rs.getString(1) + "  姓名："
									+ rs.getString(2) + "  性别："
									+ rs.getString(3) + "\n";
						}
						textAreaShow.setText(str);
					} catch (ClassNotFoundException e1)
					{
						e1.printStackTrace();
					} catch (SQLException e1)
					{
						e1.printStackTrace();
					} finally
					{
						try
						{
							if (stm != null)
							{
								stm.close();
							}
							if (conn != null)
							{
								conn.close();
							}

						} catch (SQLException e1)
						{
							e1.printStackTrace();
						}
					}

				}

				// 按学号查询
				if ((!textFieldID.getText().equals(""))
						& textFieldName.getText().equals("")
						& textFieldsex.getText().equals(""))
				{
					if (!(Pattern.matches("(\\d{10})", textFieldID.getText())))
					{
						// System.out.print(textFieldID.getText());
						JOptionPane.showMessageDialog(null, "请输入正确的学号（10位数字）！");
						return;
					}
					java.sql.Connection conn1 = null;
					PreparedStatement pstm1 = null;
					ResultSet rs = null;
					String str1 = "";
					try
					{
						Class.forName("com.mysql.jdbc.Driver");
						conn1 = DriverManager.getConnection(
								url,user,psd);
						pstm1 = conn1
								.prepareStatement("select * from stu where sID=? ");
						pstm1.setString(1, textFieldID.getText());
						rs = pstm1.executeQuery();

						if (rs.next())
						{
							rs.previous();
							while (rs.next())
							{
								str1 += "学号：" + rs.getString(1) + "  姓名："
										+ rs.getString(2) + "  性别："
										+ rs.getString(3) + "\n";
								textAreaShow.setText(str1);
							}
						} else
						{
							textAreaShow.setText("查询失败！该条信息不存在。");
						}

					} catch (ClassNotFoundException e1)
					{
						e1.printStackTrace();
					} catch (SQLException e1)
					{
						e1.printStackTrace();
					} finally
					{
						try
						{
							if (pstm1 != null)
							{
								pstm1.close();
							}
							if (conn1 != null)
							{
								conn1.close();
							}
							if (rs != null)
							{
								rs.close();
							}
						} catch (SQLException e1)
						{
							e1.printStackTrace();
						}
					}
				}

				// 按姓名查询
				if ((textFieldID.getText().equals(""))
						& !textFieldName.getText().equals("")
						& textFieldsex.getText().equals(""))
				{
					if (!(Pattern.matches("([\u4e00-\u9fa5]{2,128})",
							textFieldName.getText())))
					{
						// System.out.print(textFieldID.getText());
						JOptionPane.showMessageDialog(null, "请输入正确的中文姓名！");
						return;
					}
					java.sql.Connection conn1 = null;
					PreparedStatement pstm1 = null;
					ResultSet rs = null;
					String str1 = "";
					try
					{
						Class.forName("com.mysql.jdbc.Driver");
						conn1 = DriverManager.getConnection(
								url, user,psd);
						pstm1 = conn1
								.prepareStatement("select * from stu where sName=? ");
						pstm1.setString(1, textFieldName.getText());
						rs = pstm1.executeQuery();

						if (rs.next())
						{
							rs.previous();
							while (rs.next())
							{
								str1 += "学号：" + rs.getString(1) + "  姓名："
										+ rs.getString(2) + "  性别："
										+ rs.getString(3) + "\n";
								textAreaShow.setText(str1);
							}
						} else
						{
							textAreaShow.setText("查询失败！该条信息不存在。");
						}

					} catch (ClassNotFoundException e1)
					{
						e1.printStackTrace();
					} catch (SQLException e1)
					{
						e1.printStackTrace();
					} finally
					{
						try
						{
							if (pstm1 != null)
							{
								pstm1.close();
							}
							if (conn1 != null)
							{
								conn1.close();
							}
							if (rs != null)
							{
								rs.close();
							}

						} catch (SQLException e1)
						{
							e1.printStackTrace();
						}
					}
				}

				// 按性别查询
				if ((textFieldID.getText().equals(""))
						& textFieldName.getText().equals("")
						& !textFieldsex.getText().equals(""))
				{
					if (!(textFieldsex.getText().equals("男"))
							& !(textFieldsex.getText().equals("女")))
					{
						// System.out.println(textFieldsex.getText());
						JOptionPane.showMessageDialog(null, "请输入正确的性别（男或女）！ ");
						return;
					}
					java.sql.Connection conn1 = null;
					PreparedStatement pstm1 = null;
					String str1 = "";
					try
					{
						Class.forName("com.mysql.jdbc.Driver");
						conn1 = DriverManager.getConnection(
								url, user,psd);
						pstm1 = conn1
								.prepareStatement("select * from stu where sSex=? ");
						pstm1.setString(1, textFieldsex.getText());
						ResultSet rs = pstm1.executeQuery();

						if (rs.next())
						{
							rs.previous();
							while (rs.next())
							{
								str1 += "学号：" + rs.getString(1) + "  姓名："
										+ rs.getString(2) + "  性别："
										+ rs.getString(3) + "\n";
								textAreaShow.setText(str1);
							}
						} else
						{
							textAreaShow.setText("查询失败！该条信息不存在。");
						}

					} catch (ClassNotFoundException e1)
					{
						e1.printStackTrace();
					} catch (SQLException e1)
					{
						e1.printStackTrace();
					} finally
					{
						try
						{
							if (pstm1 != null)
							{
								pstm1.close();
							}
							if (conn1 != null)
							{
								conn1.close();
							}

						} catch (SQLException e1)
						{
							e1.printStackTrace();
						}
					}
				}

			}
		});
	}
}
