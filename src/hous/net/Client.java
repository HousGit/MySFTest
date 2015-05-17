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
	public static void main(String[] args)// �������
	{

		Client client = new Client();
		client.createFrame();
	}

	private void createFrame()// ������
	{

		Frame mFrame = new Frame("           By Hous~");

		Button buttonIn = new Button("����");
		Button buttonDel = new Button("ɾ��");
		Button buttonQur = new Button("��ѯ");

		Label labelID = new Label("ѧ��");
		Label labelName = new Label("����");
		Label labelSex = new Label("�Ա�");
		Label labelIfo = new Label("ѧ����Ϣ");
		Label labelIfo2 = new Label("���˵��");

		TextField textFieldID = new TextField("", 10);
		TextField textFieldName = new TextField("", 10);
		TextField textFieldsex = new TextField("", 10);
		TextArea textAreaShow = new TextArea();
		TextArea textAreaIfo = new TextArea();

		textAreaIfo.setEditable(false);
		// textAreaIfo.setRows(1);
		textAreaIfo.setText("1���룺����ѧ�š��������Ա�ѧ����Ϣ�������ݿ��С�\n"
				+ "2ɾ��������Ҫɾ����ѧ��ѧ��ִ��ɾ����\n" + "3��ѯ�����������ֶβ�ѯ��Ӧ��Ϣ��������ʾ���ݿ�������ѧ����Ϣ��");

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

		// TextField�����Ӧ������
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

		// buttonIn�¼���Ӧ
		buttonIn.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (!(Pattern.matches("(\\d{10})", textFieldID.getText())))
				{
					// System.out.print(textFieldID.getText());
					JOptionPane.showMessageDialog(null, "��������ȷ��ѧ�ţ�10λ���֣���");
					return;
				}
				if (!(Pattern.matches("([\u4e00-\u9fa5]{2,128})",
						textFieldName.getText())))
				{
					// System.out.print(textFieldID.getText());
					JOptionPane.showMessageDialog(null, "��������ȷ������������");
					return;
				}
				if (!(textFieldsex.getText().equals("��"))
						& !(textFieldsex.getText().equals("Ů")))
				{
					// System.out.println(textFieldsex.getText());
					JOptionPane.showMessageDialog(null, "��������ȷ���Ա��л�Ů���� ");
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
					textAreaShow.setText("����ɹ���" + "ѧ�ţ�"
							+ textFieldID.getText() + " ������"
							+ textFieldName.getText() + " �Ա�"
							+ textFieldsex.getText());

				} catch (ClassNotFoundException e1)
				{
					e1.printStackTrace();
				} catch (MySQLIntegrityConstraintViolationException e2)// �����쳣����
				{
					textAreaShow.setText("�洢ʧ�ܣ�������Ϣ�Ѵ��ڣ�");
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
					JOptionPane.showMessageDialog(null, "��������ȷ��ѧ�ţ�10λ���֣���");
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
						textAreaShow.setText("��ɾ��ѧ��Ϊ" + textFieldID.getText()
								+ "����Ϣ");
					}
					if (0 == i)
					{
						textAreaShow.setText("ɾ��ʧ�ܣ�������Ϣ�����ڡ�");
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
				// Ĭ�ϲ�ѯ��������
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
							str += "ѧ�ţ�" + rs.getString(1) + "  ������"
									+ rs.getString(2) + "  �Ա�"
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

				// ��ѧ�Ų�ѯ
				if ((!textFieldID.getText().equals(""))
						& textFieldName.getText().equals("")
						& textFieldsex.getText().equals(""))
				{
					if (!(Pattern.matches("(\\d{10})", textFieldID.getText())))
					{
						// System.out.print(textFieldID.getText());
						JOptionPane.showMessageDialog(null, "��������ȷ��ѧ�ţ�10λ���֣���");
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
								str1 += "ѧ�ţ�" + rs.getString(1) + "  ������"
										+ rs.getString(2) + "  �Ա�"
										+ rs.getString(3) + "\n";
								textAreaShow.setText(str1);
							}
						} else
						{
							textAreaShow.setText("��ѯʧ�ܣ�������Ϣ�����ڡ�");
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

				// ��������ѯ
				if ((textFieldID.getText().equals(""))
						& !textFieldName.getText().equals("")
						& textFieldsex.getText().equals(""))
				{
					if (!(Pattern.matches("([\u4e00-\u9fa5]{2,128})",
							textFieldName.getText())))
					{
						// System.out.print(textFieldID.getText());
						JOptionPane.showMessageDialog(null, "��������ȷ������������");
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
								str1 += "ѧ�ţ�" + rs.getString(1) + "  ������"
										+ rs.getString(2) + "  �Ա�"
										+ rs.getString(3) + "\n";
								textAreaShow.setText(str1);
							}
						} else
						{
							textAreaShow.setText("��ѯʧ�ܣ�������Ϣ�����ڡ�");
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

				// ���Ա��ѯ
				if ((textFieldID.getText().equals(""))
						& textFieldName.getText().equals("")
						& !textFieldsex.getText().equals(""))
				{
					if (!(textFieldsex.getText().equals("��"))
							& !(textFieldsex.getText().equals("Ů")))
					{
						// System.out.println(textFieldsex.getText());
						JOptionPane.showMessageDialog(null, "��������ȷ���Ա��л�Ů���� ");
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
								str1 += "ѧ�ţ�" + rs.getString(1) + "  ������"
										+ rs.getString(2) + "  �Ա�"
										+ rs.getString(3) + "\n";
								textAreaShow.setText(str1);
							}
						} else
						{
							textAreaShow.setText("��ѯʧ�ܣ�������Ϣ�����ڡ�");
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
