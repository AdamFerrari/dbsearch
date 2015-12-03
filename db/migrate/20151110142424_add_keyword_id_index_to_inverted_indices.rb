class AddKeywordIdIndexToInvertedIndices < ActiveRecord::Migration
  def change
    add_index :inverted_indices, :keyword_id
  end
end
