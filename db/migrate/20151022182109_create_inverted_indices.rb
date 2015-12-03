class CreateInvertedIndices < ActiveRecord::Migration
  def change
    create_table :inverted_indices do |t|
      t.integer :keyword_id
      t.integer :record_id
      t.integer :count

      t.timestamps null: false
    end
  end
end
