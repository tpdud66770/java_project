package Frame;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class EnhancedCalendar extends JDialog {
    String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    DefaultTableModel model;
    JTable table;
    JLabel monthLabel;
    GregorianCalendar cal;
    String selectedDate = null;

    public EnhancedCalendar(Window parent) {
        super(parent, "Enhanced Calendar", ModalityType.APPLICATION_MODAL);
        this.setSize(400, 300);
        this.setLayout(new BorderLayout());
        this.setResizable(false);
        setLocationRelativeTo(parent);
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        monthLabel = new JLabel();
        monthLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton prevButton = new JButton("<");
        JButton nextButton = new JButton(">");

        prevButton.addActionListener(e -> {
            cal.add(Calendar.MONTH, -1);
            updateMonth();
        });
        nextButton.addActionListener(e -> {
            cal.add(Calendar.MONTH, 1);
            updateMonth();
        });

        panel.add(prevButton, BorderLayout.WEST);
        panel.add(monthLabel, BorderLayout.CENTER);
        panel.add(nextButton, BorderLayout.EAST);

        model = new DefaultTableModel(null, days) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model) {
            @Override
            public TableCellRenderer getCellRenderer(int row, int column) {
                return new DateCellRenderer(); // 사용자 정의 렌더러 사용
            }
        };
        table.setRowHeight(40);
        JScrollPane pane = new JScrollPane(table);

        this.add(panel, BorderLayout.NORTH);
        this.add(pane, BorderLayout.CENTER);

        cal = new GregorianCalendar();
        updateMonth();

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                int column = table.getSelectedColumn();
                Object value = table.getValueAt(row, column);

                if (value != null) {
                    int day = Integer.parseInt(value.toString());
                    GregorianCalendar today = new GregorianCalendar();
                    today.set(Calendar.HOUR_OF_DAY, 0);
                    today.set(Calendar.MINUTE, 0);
                    today.set(Calendar.SECOND, 0);
                    today.set(Calendar.MILLISECOND, 0);

                    GregorianCalendar selectedDateCal = new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), day);

                    // 과거 날짜인지 확인하고 선택 불가하게 설정
                    if (!selectedDateCal.before(today)) {
                        selectedDate = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + day;
                        dispose(); // 달력 닫기
                    }
                }
            }
        });
    }

    public String getSelectedDate() {
        return selectedDate;
    }

    void updateMonth() {
        cal.set(Calendar.DAY_OF_MONTH, 1);
        monthLabel.setText(cal.getDisplayName(Calendar.MONTH, Calendar.LONG, getLocale()) + " " + cal.get(Calendar.YEAR));
        int startDay = cal.get(Calendar.DAY_OF_WEEK);
        int numberOfDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        model.setRowCount(0);
        model.setRowCount((numberOfDays + startDay - 2) / 7 + 1);

        int i = startDay - 1;
        for (int day = 1; day <= numberOfDays; day++) {
            model.setValueAt(day, (i / 7), (i % 7));
            i++;
        }
    }

    private class DateCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if (value != null) {
                int day = Integer.parseInt(value.toString());
                GregorianCalendar today = new GregorianCalendar();
                today.set(Calendar.HOUR_OF_DAY, 0);
                today.set(Calendar.MINUTE, 0);
                today.set(Calendar.SECOND, 0);
                today.set(Calendar.MILLISECOND, 0);

                GregorianCalendar date = new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), day);

                if (date.before(today)) { // 과거 날짜
                    c.setBackground(Color.LIGHT_GRAY); // 배경색 변경
                    c.setForeground(Color.DARK_GRAY); // 텍스트 색상 변경
                } else {
                    c.setBackground(Color.WHITE); // 기본 배경색
                    c.setForeground(Color.BLACK); // 기본 텍스트 색상
                }
            } else {
                c.setBackground(Color.WHITE);
                c.setForeground(Color.BLACK);
            }

            return c;
        }
    }
}