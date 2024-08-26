package log;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
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
        super(parent, "Enhanced Calendar", ModalityType.APPLICATION_MODAL);  // Window 타입 사용
        this.setSize(400, 300);
        this.setLayout(new BorderLayout());
        this.setResizable(false);

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
        table = new JTable(model);
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
                    selectedDate = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + day;
                    dispose();  // 달력 닫기
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
}
