package panen.view;

import panen.controller.PanenController;
import panen.model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SistemDataPanenView extends JFrame {

    PanenController c = new PanenController();

    CardLayout card = new CardLayout();
    JPanel main = new JPanel(card);

    JTextField txtPetani = new JTextField();
    JTextField txtLahan = new JTextField();
    JTextField txtTanaman = new JTextField();
    JTextField txtJumlah = new JTextField();
    JTextField txtTanggal = new JTextField();

    JComboBox<Petani> cbPetani = new JComboBox<>();
    JComboBox<Lahan> cbLahan = new JComboBox<>();

    DefaultTableModel mPetani, mLahan, mTanaman, mData;
    JTable tPetani, tLahan, tTanaman, tData;

    public SistemDataPanenView() {
        setTitle("Sistem Pendataan Panen");
        setSize(900, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // MENU
        JPanel menu = new JPanel();
        JButton bPetani = new JButton("Petani");
        JButton bLahan = new JButton("Lahan");
        JButton bTanaman = new JButton("Tanaman");
        JButton bData = new JButton("Data Terintegrasi");

        menu.add(bPetani); menu.add(bLahan);
        menu.add(bTanaman); menu.add(bData);
        add(menu, BorderLayout.NORTH);

        // PETANI
        mPetani = new DefaultTableModel(new String[]{"Nama"}, 0);
        tPetani = new JTable(mPetani);

        JButton ps = new JButton("Simpan");   
        JButton pe = new JButton("Edit");
        JButton ph = new JButton("Hapus");

        JPanel pPetani = new JPanel(new BorderLayout());
        JPanel fPetani = new JPanel(new GridLayout(2,2));
        fPetani.add(new JLabel("Nama Petani"));
        fPetani.add(txtPetani);
        fPetani.add(ps); 
        fPetani.add(pe);

        pPetani.add(fPetani, BorderLayout.NORTH);
        pPetani.add(new JScrollPane(tPetani), BorderLayout.CENTER);
        pPetani.add(ph, BorderLayout.SOUTH);

        // LAHAN
        mLahan = new DefaultTableModel(new String[]{"Lahan","Petani"},0);
        tLahan = new JTable(mLahan);

        JButton ls = new JButton("Simpan");     
        JButton le = new JButton("Edit");
        JButton lh = new JButton("Hapus");

        JPanel pLahan = new JPanel(new BorderLayout());
        JPanel fLahan = new JPanel(new GridLayout(3,2));
        fLahan.add(new JLabel("Nama Lahan"));
        fLahan.add(txtLahan);
        fLahan.add(new JLabel("Petani"));
        fLahan.add(cbPetani);
        fLahan.add(ls); 
        fLahan.add(le);

        pLahan.add(fLahan, BorderLayout.NORTH);
        pLahan.add(new JScrollPane(tLahan), BorderLayout.CENTER);
        pLahan.add(lh, BorderLayout.SOUTH);

        // TANAMAN
        mTanaman = new DefaultTableModel(
                new String[]{"Tanaman","Jumlah","Tanggal","Lahan","Petani"},0);
        tTanaman = new JTable(mTanaman);
   
                
        JButton ts = new JButton("Simpan");
        JButton te = new JButton("Edit");
        JButton th = new JButton("Hapus");

        JPanel pTanaman = new JPanel(new BorderLayout());
        JPanel fTanaman = new JPanel(new GridLayout(5,2));
        fTanaman.add(
                new JLabel("Nama Tanaman"));
        fTanaman.add(txtTanaman);
        fTanaman.add(new JLabel("Jumlah"));
        fTanaman.add(txtJumlah);
        fTanaman.add(new JLabel("Tanggal Panen"));
        fTanaman.add(txtTanggal);
        fTanaman.add(new  JLabel("Lahan"));
        fTanaman.add(cbLahan);
        fTanaman.add (ts); 
        fTanaman.add(te);   

        pTanaman.add(fTanaman, BorderLayout.NORTH);
        pTanaman.add(new JScrollPane(tTanaman), BorderLayout.CENTER);
        pTanaman.add(th, BorderLayout.SOUTH);

        //  DATA  
        mData = new DefaultTableModel(new String[]{"Petani", "Total Panen"}, 0);
        tData = new JTable(mData);

        JPanel pData = new JPanel(new BorderLayout());
        pData.add(new JScrollPane(tData));

        main.add(pPetani,"PETANI");
        main.add(pLahan,"LAHAN");
        main.add(pTanaman,"TANAMAN");
        main.add(pData,"DATA");
        add(main);

        // MENU EVENT
        bPetani.addActionListener(e -> card.show(main,"PETANI"));
        bLahan.addActionListener(e -> { refreshPetaniCB(); card.show(main,"LAHAN"); });
        bTanaman.addActionListener(e -> { refreshLahanCB(); card.show(main,"TANAMAN"); });
        bData.addActionListener(e -> refreshData());

        // CRUD PETANI
        ps.addActionListener(e -> exec(() -> c.tambahPetani(txtPetani.getText()), this::refreshPetani));
        pe.addActionListener(e -> exec(() -> c.editPetani(tPetani.getSelectedRow(), txtPetani.getText()), this::refreshPetani));
        ph.addActionListener(e -> exec(() -> c.hapusPetani(tPetani.getSelectedRow()), this::refreshPetani));

        // CRUD LAHAN
        ls.addActionListener(e -> exec(() -> c.tambahLahan(txtLahan.getText(), (Petani)cbPetani.getSelectedItem()), this::refreshLahan));
        le.addActionListener(e -> exec(() -> c.editLahan(tLahan.getSelectedRow(), txtLahan.getText(), (Petani)cbPetani.getSelectedItem()), this::refreshLahan));
        lh.addActionListener(e -> exec(() -> c.hapusLahan(tLahan.getSelectedRow()), this::refreshLahan));

        // CRUD TANAMAN
        ts.addActionListener(e -> exec(() -> c.tambahTanaman(
                txtTanaman.getText(), txtJumlah.getText(), txtTanggal.getText(), (Lahan)cbLahan.getSelectedItem()), this::refreshTanaman));
        te.addActionListener(e -> exec(() -> c.editTanaman(
                tTanaman.getSelectedRow(), txtTanaman.getText(), txtJumlah.getText(), txtTanggal.getText(), (Lahan)cbLahan.getSelectedItem()), this::refreshTanaman));
        th.addActionListener(e -> exec(() -> c.hapusTanaman(tTanaman.getSelectedRow()), this::refreshTanaman));
    }

    // ===== HELPER =====
    void exec(Runnable r, Runnable refresh) {
        try {
            r.run();
            refresh.run();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    void refreshPetani() {
        mPetani.setRowCount(0);
        for (Petani p : c.petaniList)
            mPetani.addRow(new Object[] { p.nama });
    }

    void refreshLahan() {
        mLahan.setRowCount(0);
        for (Lahan l : c.lahanList)
            mLahan.addRow(new Object[] { l.nama, l.petani.nama });
    }

    void refreshTanaman() {
        mTanaman.setRowCount(0);
        for (Tanaman t : c.tanamanList)
            mTanaman.addRow(new Object[] { t.nama, t.jumlah, t.tanggal, t.lahan.nama, t.lahan.petani.nama });
    }

    void refreshPetaniCB() {
        cbPetani.removeAllItems();
        for (Petani p : c.petaniList)
            cbPetani.addItem(p);
    }

    void refreshLahanCB() {
        cbLahan.removeAllItems();
        for (Lahan l : c.lahanList)
            cbLahan.addItem(l);
    }

    void refreshData() {
        mData.setRowCount(0);
        for (Petani p : c.petaniList)
            mData.addRow(new Object[] { p.nama, c.totalPanenPetani(p) });
        card.show(main, "DATA");
    }
}
